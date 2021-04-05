package study.kstock.stockcore.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import study.kstock.stockcore.message.ResponseFactory
import study.kstock.stockcore.model.StockData
import study.kstock.stockcore.model.StockMarket
import study.kstock.stockcore.service.search.MarketListByRegionSearching
import study.kstock.stockcore.service.search.TwentyStockDataSearching
import javax.annotation.Resource

@RestController
class APIController {

    // TODO SearchStockService 인터페이스 하나만으로 가능한 방법 없을지 확인
    @Resource private lateinit var searchMarketListByRegion: MarketListByRegionSearching
    @Resource private lateinit var search20StockDataSearching: TwentyStockDataSearching

    @GetMapping("market/list/{region}")
    fun getMarketListBy(@PathVariable region: String): ResponseEntity<MutableList<StockMarket>> {
        val result =  searchMarketListByRegion.search(region)
        return ResponseFactory.createResponse(result)

    }

    @GetMapping("stock/list/{market}/{start}")
    fun getArrayOf20Stocks(@PathVariable market: String, @PathVariable start: Int): ResponseEntity<MutableList<StockData>> {
        val result = search20StockDataSearching.search(market, start)
        return ResponseFactory.createResponse(result)
    }

}