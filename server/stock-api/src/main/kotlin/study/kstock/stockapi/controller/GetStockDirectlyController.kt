package study.kstock.stockapi.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import study.kstock.stockapi.domain.StockData
import study.kstock.stockapi.message.ResponseFactory
import study.kstock.stockapi.service.StockDataCrawlingService
import javax.annotation.Resource

@RestController
class GetStockDirectlyController {

    @Resource
    private lateinit var stockDataCrawlingService: StockDataCrawlingService

    @GetMapping("search/{symbol}")
    fun getStockListBySymbol(@PathVariable symbol: String): ResponseEntity<StockData> {
        val result = stockDataCrawlingService.searchStockData(symbol)
        return ResponseFactory.createResponse(result)
    }

    @GetMapping("stock/list/{market}/{start}")
    suspend fun getArrayOf20Stocks(@PathVariable market: String, @PathVariable start: Int): ResponseEntity<MutableList<StockData>> {
        val result = stockDataCrawlingService.getRecentStockDataListOf(market, start)
        return ResponseFactory.createResponse(result)
    }

    @PostMapping("search/list")
    fun getStockListBySymbols(@RequestBody searchedTexts: Map<String, Array<String>>): ResponseEntity<MutableList<StockData>> {
        val result = stockDataCrawlingService.searchStocks(searchedTexts.getOrDefault("data", arrayOf()))
        return ResponseFactory.createResponse(result)
    }
}