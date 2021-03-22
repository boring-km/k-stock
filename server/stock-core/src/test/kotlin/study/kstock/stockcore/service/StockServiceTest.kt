package study.kstock.stockcore.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

@SpringBootTest
class StockServiceTest {

    @Resource
    private lateinit var stockService: StockService

    private val logger: Logger = org.slf4j.LoggerFactory.getLogger(StockServiceTest::class.java)

    @DisplayName("United States에 있는 거래소의 갯수를 조회하면 4다")
    @Test
    internal fun getMarketListByTest() {
        // given
        val region = "United States"

        // when
        val marketList = stockService.getMarketListBy(region)
        marketList.forEach { stockMarket -> logger.info("${region}에 있는 거래소: $stockMarket") }
        val size = marketList.size
        val expected = 4

        // then
        assertThat(size).isEqualTo(expected)
    }

    @DisplayName("NYSE 거래소의 주어진 시작 인덱스 0부터 20개의 주식 데이터를 가져올 수 있다")
    @Test
    internal fun getArrayOf20StocksTest() {

        // given
        val marketName = "NYSE"
        val startIndex = 0

        // when
        val result = stockService.getArrayOf20Stocks(marketName, startIndex)
        result.forEach { stockData ->
            logger.info("$marketName StockData: $stockData")
        }

        // then
        assertThat(result).isNotEmpty
    }
}