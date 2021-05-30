package study.kstockapp.service

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
        context: Context,
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
                    val sharedPrefManager = SharedPrefManager(context, "searched")
                    sharedPrefManager.saveStringSet("data", data.stockSymbol.symbol)

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
        context: Context,
        service: KStockService,
        binding: ActivityMainBinding,
        dataName: String
    ) {
        val sharedPrefManager = SharedPrefManager(context, dataName)
        val dataArray = sharedPrefManager.findStringSet("data")!!.toTypedArray()

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