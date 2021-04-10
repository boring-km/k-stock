package study.kstock.stockapi.controller

import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import study.kstock.stockapi.message.ResponseFactory
import study.kstock.stockapi.service.StockService
import javax.annotation.Resource


@RestController
@Api(value = "StockServiceController")
class StockServiceController {  // stock-core 서버에 요청하여 결과를 다시 반환할 뿐인 컨트롤러

    @Resource
    private lateinit var stockService: StockService

    @GetMapping("market/list/{region}")
    suspend fun getMarketListByRegion(@PathVariable region: String): ResponseEntity<MutableList<Any>> {
        val result = stockService.getMarketListBy(region)
        return ResponseFactory.createResponse(result)
    }

}
