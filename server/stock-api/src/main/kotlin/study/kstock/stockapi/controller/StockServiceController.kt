package study.kstock.stockapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class StockServiceController {

    @GetMapping("market/list/{region}")
    fun getStockMarketList(@PathVariable region: String): Array<String> {
        TODO("stock-core 서버에 요청(kafka?)하여 해당 지역의 모든 거래소 리스트를 반환하자")
    }
}