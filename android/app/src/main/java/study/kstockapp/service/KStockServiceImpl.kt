package study.kstockapp.service

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import study.kstockapp.adapter.StockAdapter
import study.kstockapp.databinding.ActivityMainBinding
import study.kstockapp.domain.StockData
import study.kstockapp.domain.StockNameArray
import study.kstockapp.network.KStockService
import javax.inject.Inject

class KStockServiceImpl @Inject constructor() {

    fun searchStock(
        service: KStockService,
        searchText: String,
        binding: ActivityMainBinding
    ) {
        service.getStockBySymbol(searchText).enqueue(object : Callback<StockData> {
            override fun onResponse(
                call: Call<StockData>,
                response: Response<StockData>
            ) {
                if (response.isSuccessful) {
                    val data = response.body() as StockData
                    val prefs = binding.root.context.getSharedPreferences("searched", Context.MODE_PRIVATE)
                    val tempSet: MutableSet<String>? = prefs.getStringSet("data", HashSet<String>())
                    tempSet!!.add(data.stockSymbol.symbol)

                    val editor = prefs.edit()
                    editor.putStringSet("data", tempSet)
                    editor.apply()

                    binding.searchedStockRecyclerView.apply {
                        (adapter as StockAdapter).add(data)
                    }
                }
            }

            override fun onFailure(call: Call<StockData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getSearchedStocks(
        service: KStockService,
        binding: ActivityMainBinding
    ) {
        val prefs = binding.root.context.getSharedPreferences("searched", Context.MODE_PRIVATE)
        val dataArray = prefs.getStringSet("data", HashSet<String>())!!.toTypedArray()
        service.getStocksBySymbols(StockNameArray(dataArray))
            .enqueue(object : Callback<List<StockData>> {
                override fun onResponse(
                    call: Call<List<StockData>>,
                    response: Response<List<StockData>>
                ) {
                    if (response.isSuccessful) {
                        val dataList = response.body() as List<StockData>
                        binding.searchedStockRecyclerView.apply {
                            (adapter as StockAdapter).addAll(dataList)
                        }
                    } else {
                        Log.d("responseError", response.message())
                    }
                }

                override fun onFailure(call: Call<List<StockData>>, t: Throwable) {
                    t.printStackTrace()
                }

            })

    }
}