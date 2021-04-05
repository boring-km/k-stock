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

    @DisplayName("AEVA 주식의 현재 가격은 15.08달러다")
    @Test
    internal fun getRecentPriceTest() {
        val resultPrice = stockPriceService.getRecentPriceOf("AEVA")
        logger.info("현재 가격: $resultPrice")
        assertThat(resultPrice).isEqualTo(12.31)
    }

    @DisplayName("아마존 애플 AEVA 주식의 현재 주가를 가져온다")
    @Test
    internal fun getRecentPriceArrayTest() {
        val targetStockNameArray = arrayOf("AMZN", "AAPL", "AEVA")
        val region = "US"
        val resultPriceArray = stockPriceService.getRecentPriceArrayOf(targetStockNameArray, region)
        assertThat(resultPriceArray).hasSize(3)
    }

    @DisplayName("1페이지부터 157페이지 끝까지 USA 지역의 나스닥 주식데이터를 웹크롤링해서 가져오는데 걸리는 시간은 130초 ~ 150초 사이이다")
    @Test
    internal fun getStockDataTimeCheckTest() {
        // given
        val market = "nasd"
        val region = "usa"
        val startNum = 1
        val endNum = 157

        // when
        val startTime = System.currentTimeMillis()
        val stockDataList = stockPriceService.getRecentStockDataListOf(market, region, startNum, endNum)
        val endTime = System.currentTimeMillis()
        val timeTaken = endTime-startTime
        logger.info("걸린시간 ${timeTaken / 1000}초")
        stockDataList.forEach { stockData -> logger.info(stockData.toString()) }

        // then
        assertThat(timeTaken).isBetween(1300000, 1500000)
    }
}
