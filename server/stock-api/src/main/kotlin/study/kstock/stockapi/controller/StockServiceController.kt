package study.kstock.stockapi.controller

import org.springframework.web.bind.annotation.*

@RestController
class StockServiceController {  // stock-core 서버에 요청하여 결과를 다시 반환할 뿐인 컨트롤러

    @GetMapping("market/list/{region}")
    fun getStockMarketList(@PathVariable region: String): Array<String> {
        TODO("해당 지역의 모든 거래소 리스트를 가져와서 반환")
    }

    @GetMapping("market/list/{region}/{start}/{end}")
    fun getStockList(@PathVariable region: String, @PathVariable start: Int, @PathVariable end: Int): Array<Any> {
        TODO("해당 지역의 주식들을 종목코드, 종목명, 현재가 등을 가져와서 반환")
    }

    @GetMapping("stock/{region}/{symbol}")
    fun getStockInfo(@PathVariable region: String, @PathVariable symbol: String): Array<Any> {
        TODO("해당 지역의 주식 종목명을 입력받아 상세 정보를 가져와서 반환 (내용이 너무 많으면 앱에서 보여줄 WebView의 URL도 괜찮을 것 같다.)")
    }

    @GetMapping("mystock")
    fun getMyStockList(): Array<Any> {
        // 세션 정보를 읽어 고객 ID 값과 함께 서비스 요청
        TODO("사용자가 등록한 주식의 이름, 종목코드, 현재가, 사용자 매수가, 알림 설정 조건, 알림 설정 여부 등을 가져와서 반환")
    }

    @PutMapping("register")
    fun registerMyStock(@RequestBody stockData: Any): Boolean {
        TODO("stockData에 담긴 주식 정보를 등록하고 결과를 반환, 여러 개 들어올 수 있음")
    }

    @PatchMapping("update")
    fun updateMyStock(@RequestBody stockData: Any): Boolean {
        TODO("stockData에 담긴 주식 정보를 수정하고 결과를 반환, 한 개만 수정")
    }

    @DeleteMapping("delete")
    fun deleteMyStock(@RequestBody stockData: Any): Boolean {
        // http://hwannnn.blogspot.com/2018/07/putdeletemapping-body.html Delete Mapping body 파싱 설정
        TODO("stockData에 담긴 주식 정보들을 그 유저의 주식 정보에서 삭제하고 결과를 반환")
    }
}
