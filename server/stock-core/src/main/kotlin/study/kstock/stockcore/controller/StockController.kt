package study.kstock.stockcore.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import study.kstock.stockcore.model.StockMarket
import study.kstock.stockcore.model.StockService

@RestController
class StockController {

    @Autowired
    lateinit var service: StockService

    @GetMapping("find/{market}")
    fun getMarket(@PathVariable market: String): StockMarket {
        return service.getMarket(market)
    }
}