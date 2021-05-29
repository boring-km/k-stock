package study.kstock.stockapi.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import study.kstock.stockapi.domain.StockData

@SpringBootTest
internal class StockDataCrawlingServiceTest {

    @Autowired
    private lateinit var stockDataCrawlingService: StockDataCrawlingService
    private val logger: Logger = LoggerFactory.getLogger(StockDataCrawlingServiceTest::class.java)

    @DisplayName("AEVA 주식을 검색하면 null이 아니다")
    @Test
    fun searchStockData() {
        // given
        val symbol = "AEVA"

        // when
        val result: StockData = stockDataCrawlingService.searchStockData(symbol)
        logger.info("조회 결과: $result")

        // then
        assertThat(result).isNotNull
    }

    @DisplayName("인덱스 1번부터 20번까지 나스닥 주식데이터를 웹크롤링해서 가져오는데 걸리는 시간은 3초 미만이다")
    @Test
    internal fun getStockDataTimeCheckTest() {
        // given
        val market = "nasd"
        val start = 1

        // when
        val startTime = System.currentTimeMillis()
        val endTime = System.currentTimeMillis()
        val timeTaken = endTime - startTime
        logger.info("걸린시간 ${timeTaken.toDouble() / 1000}초")
        stockDataCrawlingService.getRecentStockDataListOf(market, start)
            ?.forEach { stockData -> logger.info(stockData.toString()) }

        // then
        assertThat(timeTaken).isLessThan(3000)
    }

    @DisplayName("넷플릭스, 애플, 구글 주식을 같이 조회하는 테스트")
    @Test
    internal fun searchStocksTest() {
        val values = arrayOf("NFLX", "AAPL", "GOOGL")

        val result = stockDataCrawlingService.searchStocks(values)
        result.forEach { stockData -> println(stockData.toString()) }

        assertThat(result).isNotNull
    }
}