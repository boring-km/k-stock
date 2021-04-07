package study.kstockapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import study.kstockapp.databinding.ActivityMainBinding
import study.kstockapp.network.KStockService
import study.kstockapp.network.RetrofitClient

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        val testStockItem = arrayOf("NYSE", "NASDAQ", "AMEX")
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

        // 샘플 데이터 삽입
        val sampleStockData = arrayListOf<StockData>()
        for (i in 1..2) {
            sampleStockData.add(StockData("주식데이터 $i 번째", "NYSE", 30.25, 0.0, 0.0))
        }

        binding.recyclerviewSearchedStock.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = StockAdapter(sampleStockData) { stock ->
                Toast.makeText(this@MainActivity, "$stock", Toast.LENGTH_SHORT).show()
                val intent = Intent(baseContext, StockDetail::class.java)
                intent.putExtra("obj", stock)
                startActivity(intent)
            }
        }

        val service = RetrofitClient.getInstance().create(KStockService::class.java)

        binding.editTextStockName.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    service.getStockListByMarketWithIndex(binding.spinnerStockMarket.selectedItem.toString())
                        .enqueue(object : Callback<StockData> {
                            override fun onResponse(
                                call: Call<StockData>,
                                response: Response<StockData>
                            ) {
                                if (response.isSuccessful()) {
                                    (binding.recyclerviewSearchedStock.adapter as StockAdapter).resetRecyclerView()
                                    Log.d(TAG, "onResponse body: ${response.body()}")

                                } else {
                                    Log.d(TAG, "response Code : ${response.code()}")
                                }
                            }

                            override fun onFailure(call: Call<StockData>, t: Throwable) {
                                Toast.makeText(
                                    applicationContext,
                                    "Retrofit Request Error",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d(TAG, "onFailure: " + t.printStackTrace())
                            }
                        })
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