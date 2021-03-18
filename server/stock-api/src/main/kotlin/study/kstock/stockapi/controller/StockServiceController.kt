package study.kstock.stockapi.controller

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import study.kstock.stockapi.service.StockService
import javax.annotation.Resource


@RestController
@Api(value = "StockServiceController")
class StockServiceController {  // stock-core 서버에 요청하여 결과를 다시 반환할 뿐인 컨트롤러

    @Resource
    lateinit var stockService: StockService

    @GetMapping("test/list")
    suspend fun getStockMarketListTest(): Array<Any> {
        return stockService.getStockMarketListTest()
    }

    @GetMapping("market/list/{region}")
    suspend fun getMarketListByRegion(@PathVariable region: String): Array<Any> {
        return stockService.getMarketListByRegion(region)
    }

    @GetMapping("stock/list/{market}/{start}")
    suspend fun getArrayOf20Stocks(@PathVariable market: String, @PathVariable start: Int): Array<Any> {
        return stockService.getArrayOf20Stocks(market, start)
    }

}
