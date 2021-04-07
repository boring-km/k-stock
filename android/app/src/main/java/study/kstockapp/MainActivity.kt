package study.kstockapp

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
import study.kstockapp.databinding.ActivityMainBinding
import study.kstockapp.domain.StockData
import study.kstockapp.network.KStockService
import study.kstockapp.network.RetrofitClient
import study.kstockapp.service.KStockServiceImpl

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val service = RetrofitClient.getInstance().create(KStockService::class.java)
    private val stockService = KStockServiceImpl()  // TODO 나중에 Dagger로
    private var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        val testStockItem = arrayOf("nyse", "nasd", "amex")
        binding.spinnerStockMarket.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, testStockItem)
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
                Toast.makeText(this@MainActivity, "$stock", Toast.LENGTH_SHORT).show()
                val intent = Intent(baseContext, StockDetail::class.java)
                intent.putExtra("obj", stock)
                startActivity(intent)
            }
        }

        binding.editTextStockName.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val market = binding.spinnerStockMarket.selectedItem.toString()
                    // TODO("메인 스레드에서 동기적으로 구현해서 현재 오류 발생함")
                    //  -> Handler 스레드를 사용하거나, enqueue()를 사용한 콜백으로 구현 필요함
                    val result = stockService.getStockListByMarketWithIndex(service, market, index)
                    // TODO("result 값을 사용해서 RecyclerView 보여주기")
                    binding.recyclerviewSearchedStock.apply {
                        (adapter as StockAdapter).addAll(result)
                    }

                    return@setOnEditorActionListener true
                }
                else -> {
                    Toast.makeText(applicationContext, "different Action caught", Toast.LENGTH_SHORT).show()
                    return@setOnEditorActionListener true
                }
            }
        }
    }
}