package study.kstock.stockapi.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import study.kstock.stockapi.domain.StockData
import study.kstock.stockapi.message.ResponseFactory
import study.kstock.stockapi.service.StockDataCrawlingService
import javax.annotation.Resource

@RestController
class GetStockDirectlyController {

    @Resource
    private lateinit var stockDataCrawlingService: StockDataCrawlingService

    @GetMapping("search/{symbol}")
    fun getStockListBySymbol(@PathVariable symbol: String): ResponseEntity<StockData?> {
        val result = stockDataCrawlingService.searchStockData(symbol)
        return ResponseFactory.createResponse(result)
    }

    @GetMapping("stock/list/{market}/{start}")
    suspend fun getArrayOf20Stocks(@PathVariable market: String, @PathVariable start: Int): ResponseEntity<ArrayList<StockData>?> {
        val result = stockDataCrawlingService.getRecentStockDataListOf(market, start)
        return ResponseFactory.createResponse(result)
    }
}