package study.kstock.stockapi.controller

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.*
import study.kstock.stockapi.domain.StockData
import study.kstock.stockapi.service.StockService
import javax.annotation.Resource


@RestController
@Api(value = "StockServiceController")
class StockServiceController {  // stock-core 서버에 요청하여 결과를 다시 반환할 뿐인 컨트롤러

    @Resource
    lateinit var stockService: StockService

    @GetMapping("test/list")
    suspend fun getStockMarketListTest(): Array<StockData> {
        return stockService.getStockMarketListTest()
    }

    @GetMapping("stock/list/{market}/{start}")
    fun getArrayOf20Stocks(@PathVariable market: String, @PathVariable start: Int): Array<StockData> {
        return stockService.getArrayOf20Stocks()
    }

}
