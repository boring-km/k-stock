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

    @GetMapping("market/list/{region}")
    fun getStockMarketList(@PathVariable region: String): Array<String> {
        // TODO("http, 해당 지역의 모든 거래소 리스트를 가져와서 반환")
        return stockService.getStockMarketList(region)
    }

    @GetMapping("market/list/{region}/{start}/{end}")
    fun getStockList(@PathVariable region: String, @PathVariable start: Int, @PathVariable end: Int): Array<Any> {
        TODO("http, 해당 지역의 주식들을 종목코드, 종목명, 현재가 등을 가져와서 반환" +
                "ex) 나스닥 주식 1부터 20번째에 해당되는 데이터 리턴")
    }

    @GetMapping("stock/{symbol}")
    fun getStockInfo(@PathVariable symbol: String): Array<Any> {
        TODO("http, 주식 symbol을 입력받아 상세 정보를 가져와서 반환 (내용이 너무 많으면 앱에서 보여줄 WebView의 URL도 괜찮을 것 같다.)")
    }

    @GetMapping("mystock")
    fun getMyStockList(): Array<Any> {
        // 세션 정보를 읽어 함께 서비스 요청
        TODO("http, 사용자가 등록한 주식의 이름, 종목코드, 현재가, 사용자 매수가, 알림 설정 조건, 알림 설정 여부 등을 가져와서 반환")
    }

    @PutMapping("register")
    fun registerMyStock(@RequestBody symbol: String): Boolean {
        val userId = "kangmin"  // userID를 세션에서 얻었다고 가정
        return stockService.saveStockIntoMyStock(symbol, userId)
    }

    @PatchMapping("update")
    fun updateMyStock(@RequestBody stockData: Any): Boolean {
        TODO("Kafka, stockData에 담긴 주식 정보를 수정하고 결과를 반환, 한 개만 수정")
    }

    @DeleteMapping("delete")
    fun deleteMyStock(@RequestBody stockData: Any): Boolean {
        // http://hwannnn.blogspot.com/2018/07/putdeletemapping-body.html Delete Mapping body 파싱 설정
        TODO("Kafka, stockData에 담긴 주식 정보들을 그 유저의 주식 정보에서 삭제하고 결과를 반환")
    }
}
