package study.kstock.stockcore.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import study.kstock.stockcore.model.StockData
import study.kstock.stockcore.model.StockMarket
import study.kstock.stockcore.service.StockService
import javax.annotation.Resource

@RestController
class APIController {

    @Resource private lateinit var service: StockService

    @GetMapping("market/list/{region}")
    fun getMarketListBy(@PathVariable region: String): MutableList<StockMarket> {
        return service.getMarketListBy(region)
    }

    @GetMapping("stock/list/{market}/{start}")
    fun getArrayOf20Stocks(@PathVariable market: String, @PathVariable start: String): MutableList<StockData> {
        return service.getArrayOf20Stocks(market, start)
    }

}