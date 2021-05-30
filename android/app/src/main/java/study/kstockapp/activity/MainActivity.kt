package study.kstockapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import study.kstockapp.R
import study.kstockapp.adapter.StockAdapter
import study.kstockapp.databinding.ActivityMainBinding
import study.kstockapp.domain.StockData
import study.kstockapp.network.KStockService
import study.kstockapp.network.RetrofitClient
import study.kstockapp.service.DaggerKStockServiceComponent
import study.kstockapp.service.KStockServiceImpl
import study.kstockapp.service.SharedPrefManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val service = RetrofitClient.getInstance().create(KStockService::class.java)
    private val stockService: KStockServiceImpl =
            DaggerKStockServiceComponent.create().getServiceImpl()

    private val interestName = "interest"
    private val searchName = "searched"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main
        )


        binding.searchedStockRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = StockAdapter(ArrayList()) { stock: StockData ->
                val dialogBuilder = AlertDialog.Builder(this@MainActivity)
                    .setTitle("동작 선택")
                    .setPositiveButton("자세한 정보 보기") { dialog, _ ->
                        val intent = Intent(this@MainActivity, DetailWebViewActivity::class.java)
                        intent.putExtra("symbol", stock.stockSymbol.name)
                        startActivity(intent)
                        dialog.dismiss()
                    }
                    .setNegativeButton("관심종목 등록") { dialog, _ ->
                        val sharedPrefManager = SharedPrefManager(applicationContext, interestName)
                        sharedPrefManager.saveStringSet("data", stock.stockSymbol.symbol)
                        Toast.makeText(applicationContext, "관심종목으로 ${stock.stockSymbol.name}을 등록합니다.", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                val dialog = dialogBuilder.create()
                dialog.show()
            }
        }

        binding.stockNameEditText.setOnEditorActionListener { _, actionId, _ ->
            val searchText = binding.stockNameEditText.text.toString()
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if (searchText.isEmpty()) {
                        Toast.makeText(applicationContext, "최근 검색 종목을 조회합니다.", Toast.LENGTH_SHORT).show()
                        stockService.getSearchedStocks(applicationContext, service, binding, searchName)
                    } else
                        stockService.searchStock(applicationContext, service, searchText, binding)
                    return@setOnEditorActionListener true
                }
                else -> {
                    Toast.makeText(applicationContext, "different Action caught", Toast.LENGTH_SHORT).show()
                    return@setOnEditorActionListener true
                }
            }
        }

        binding.searchButton.setOnClickListener {
            val state = binding.stockNameEditText.isVisible
            if (state) binding.stockNameEditText.visibility = View.GONE
            else binding.stockNameEditText.visibility = View.VISIBLE
        }

        binding.interestButton.setOnClickListener {
            Toast.makeText(applicationContext, "관심종목을 조회합니다.", Toast.LENGTH_SHORT).show()
            stockService.getSearchedStocks(applicationContext, service, binding, interestName)
        }
    }

    override fun onStart() {
        super.onStart()
        // 초기 화면 세팅
        stockService.getSearchedStocks(applicationContext, service, binding, searchName)
    }
}