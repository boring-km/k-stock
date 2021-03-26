package study.kstock.stockcore.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import study.kstock.stockcore.model.StockData
import study.kstock.stockcore.model.StockMarket
import study.kstock.stockcore.service.search.Search20StockDataArray
import study.kstock.stockcore.service.search.SearchMarketListByRegion
import javax.annotation.Resource

@RestController
class APIController {

    // TODO SearchStockService 인터페이스 하나만으로 가능한 방법 없을지 확인
    @Resource private lateinit var searchMarketListByRegion: SearchMarketListByRegion
    @Resource private lateinit var search20StockDataArray: Search20StockDataArray

    @GetMapping("market/list/{region}")
    fun getMarketListBy(@PathVariable region: String): MutableList<StockMarket> {
        return searchMarketListByRegion.search(region)
    }

    @GetMapping("stock/list/{market}/{start}")
    fun getArrayOf20Stocks(@PathVariable market: String, @PathVariable start: Int): MutableList<StockData> {
        return search20StockDataArray.search(market, start)
    }

}