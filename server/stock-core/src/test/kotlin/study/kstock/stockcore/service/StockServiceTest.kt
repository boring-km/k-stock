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
    lateinit var stockService: StockService

    private val logger: Logger = org.slf4j.LoggerFactory.getLogger(StockServiceTest::class.java)

    @DisplayName("United States에 있는 거래소의 갯수를 조회하면 4다")
    @Test
    internal fun getMarketListByTest() {
        // given
        val region = "United States"

        // when
        val marketList = stockService.getMarketListBy(region)
        val size = marketList.size
        val expected = 4

        // then
        assertThat(size).isEqualTo(expected)
    }

    @Test
    internal fun getStockDataListTest() {
        stockService.getArrayOf20Stocks("AEVA", 0).forEach {
            stockData -> logger.info("AnyData: $stockData")
        }
    }
}