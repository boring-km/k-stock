package study.kstockapp.service

import study.kstockapp.domain.StockData
import study.kstockapp.network.KStockService

class KStockServiceImpl {

    fun getStockListByMarketWithIndex (
        service: KStockService,
        market: String,
        index: Int
    ): ArrayList<StockData> {
        val result = ArrayList<StockData>()
        val stockListResponse = service.getStockListByMarketWithIndex(market, index).execute()
        if (stockListResponse.isSuccessful) {
            val responseList = stockListResponse.body() as List<StockData>
            println("주식 데이터 출력")
            responseList.forEach { stockData ->
                println("$stockData")
            }
            result.addAll(responseList)
        } else {
            println("response Code : ${stockListResponse.code()}")
        }
        return result
    }
}