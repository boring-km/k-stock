package study.kstock.stockexternal

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import study.kstock.stockexternal.service.StockPriceService
import javax.annotation.Resource


@SpringBootTest
class StockPriceGetTest {

    private val logger: Logger = LoggerFactory.getLogger(StockPriceGetTest::class.java)

    @Resource
    lateinit var stockPriceService: StockPriceService

    @Test
    internal fun IPV_주식의_현재_가격은_10_달러보다_크다() {
        val resultPrice = stockPriceService.getRecentPriceOf("IPV")
        logger.info("현재 가격: $resultPrice")
        assertThat(resultPrice).isGreaterThan(10.0)
    }

    @Test
    internal fun 아마존_애플_IPV_주식의_현재_주가를_가져온다() {
        val targetStockNameArray = arrayOf("AMZN", "APPL", "IPV")
        val resultPriceArray = stockPriceService.getRecentPriceArrayOf(targetStockNameArray)
        assertThat(resultPriceArray).hasSize(3)
    }
}
