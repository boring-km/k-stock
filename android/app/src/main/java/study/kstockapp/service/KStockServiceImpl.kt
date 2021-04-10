package study.kstockapp.service

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import study.kstockapp.domain.StockData
import study.kstockapp.network.KStockService
import javax.inject.Inject

class KStockServiceImpl @Inject constructor() {

    fun getStockListByMarketWithIndex (
        service: KStockService,
        market: String,
        index: Int
    ): ArrayList<StockData> {
        val result = ArrayList<StockData>()
        service.getStockListByMarketWithIndex(market, index).enqueue(object : Callback<List<StockData>> {
            override fun onResponse(
                call: Call<List<StockData>>,
                response: Response<List<StockData>>
            ) {
                if (response.isSuccessful) {
                    val responseList = response.body() as List<StockData>
                    result.addAll(responseList)
                }
            }

            override fun onFailure(call: Call<List<StockData>>, t: Throwable) {
                println("Print Stack Trace : ${t.printStackTrace()}")
            }
        })
        return result
    }
}