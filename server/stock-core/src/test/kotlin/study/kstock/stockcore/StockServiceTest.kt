package study.kstock.stockcore

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.boot.test.context.SpringBootTest
import study.kstock.stockcore.model.StockService
import javax.annotation.Resource

@SpringBootTest
class StockServiceTest {

    @Resource
    lateinit var stockService: StockService

    private val logger: Logger = org.slf4j.LoggerFactory.getLogger(StockServiceTest::class.java)

    @Test
    internal fun 모든_도시의_거래소를_가져오면_저장된_데이터가_있다() {
        val allMarketList = stockService.getMarketList()
        logger.info("allMarketList size: ${allMarketList.size}")
        assertThat(allMarketList).isNotEmpty
    }
}