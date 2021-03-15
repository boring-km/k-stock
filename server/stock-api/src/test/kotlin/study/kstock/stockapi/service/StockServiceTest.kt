package study.kstock.stockapi.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class StockServiceTest {

    @Autowired
    private lateinit var stockService: StockService

    @DisplayName("카프카 통신으로 stock-core에 kangmin 사용자의 APPL 주식을 추가할 수 있다")
    @Test
    fun saveStockIntoMyStock() {
        val result = stockService.saveStockIntoMyStock("APPL", "kangmin")
        assertTrue(result)
    }
}