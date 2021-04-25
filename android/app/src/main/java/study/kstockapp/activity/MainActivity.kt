package study.kstockapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val service = RetrofitClient.getInstance().create(KStockService::class.java)
    private val stockService: KStockServiceImpl =
            DaggerKStockServiceComponent.create().getServiceImpl()
    private var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main
        )

        val testStockItem = arrayOf("NYSE", "NASDAQ", "AMEX")
        binding.spinnerStockMarket.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, testStockItem)
        binding.spinnerStockMarket.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "시장 중 하나를 선택해주세요", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                val bindViewText =
                        "You pressed position $position, Market ${binding.spinnerStockMarket.selectedItem}"
                binding.textView.text = bindViewText
            }
        }

        binding.recyclerviewSearchedStock.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = StockAdapter(ArrayList()) { stock: StockData ->
                val intent = Intent(this@MainActivity, DetailWebViewActivity::class.java)
                intent.putExtra("symbol", stock.stockSymbol.name)
                startActivity(intent)
            }
        }

        binding.editTextStockName.setOnEditorActionListener { _, actionId, _ ->
            val searchText = binding.editTextStockName.text.toString()
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if (searchText.isEmpty())
                        stockService.getStockListByMarketWithIndex(service, index, binding)
                    else
                        stockService.getStockBySymbol(service, searchText, binding)
                    return@setOnEditorActionListener true
                }
                else -> {
                    Toast.makeText(
                            applicationContext,
                            "different Action caught",
                            Toast.LENGTH_SHORT
                    ).show()
                    return@setOnEditorActionListener true
                }
            }
        }
    }
}