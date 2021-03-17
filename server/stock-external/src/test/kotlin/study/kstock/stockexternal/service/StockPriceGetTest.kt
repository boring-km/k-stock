package study.kstock.stockexternal.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource


@SpringBootTest
class StockPriceGetTest {

    private val logger: Logger = LoggerFactory.getLogger(StockPriceGetTest::class.java)

    @Resource
    lateinit var stockPriceService: StockPriceService

    @DisplayName("AEVA_주식의_현재_가격은_15.08달러다")
    @Test
    internal fun getRecentPriceTest() {
        val resultPrice = stockPriceService.getRecentPriceOf("AEVA")
        logger.info("현재 가격: $resultPrice")
        assertThat(resultPrice).isEqualTo(15.08)
    }

    @DisplayName("아마존_애플_AEVA_주식의_현재_주가를_가져온다")
    @Test
    internal fun getRecentPriceArrayTest() {
        val targetStockNameArray = arrayOf("AMZN", "AAPL", "AEVA")
        val region = "US"
        val resultPriceArray = stockPriceService.getRecentPriceArrayOf(targetStockNameArray, region)
        assertThat(resultPriceArray).hasSize(3)
    }
}
