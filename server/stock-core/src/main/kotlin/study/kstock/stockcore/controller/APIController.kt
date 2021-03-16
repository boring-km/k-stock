package study.kstock.stockcore.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import study.kstock.stockcore.model.StockData
import study.kstock.stockcore.model.StockMarket
import study.kstock.stockcore.model.StockSymbol
import study.kstock.stockcore.service.StockService
import javax.annotation.Resource

@RestController
class APIController {

    @Resource private lateinit var service: StockService

    @GetMapping("market/list")
    fun getMarketList(): MutableList<StockMarket> {
        return service.getMarketList()
    }

    // TODO: 주식 종류가 매우 많기 때문에
    @GetMapping("symbol/list")
    fun getSymbolList(): MutableList<StockSymbol> {
        return service.getStockSymbolList()
    }

    @GetMapping("stock/list/all")
    fun getStockList(): MutableList<StockData> {
        return service.getStockDataList()
    }

}