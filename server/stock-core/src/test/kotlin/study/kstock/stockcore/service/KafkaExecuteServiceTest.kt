package study.kstock.stockcore.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import study.kstock.stockcore.model.*
import javax.annotation.Resource

@SpringBootTest
class KafkaExecuteServiceTest {

    @Autowired
    private lateinit var commandMappingService: CommandMappingService

    // TODO 테스트 코드에서 Repository 사용이 가능한지는 확인 바람
    //  없을 시 구현 코드에 테스트를 위한 Repository 생성 필요
    @Repository
    interface TestStockDataRepository: JpaRepository<StockData, Int> {
        @Query("select data from StockData data where data.stockSymbol.symbol = :symbol")
        fun findBySymbolName(@Param("symbol") name: String): StockData
    }

    @Resource lateinit var testStockDataRepository: TestStockDataRepository

    @DisplayName("AEVA 주식의 데이터를 12.36으로 업데이트 후 다시 조회한 결과가 동일한지 테스트한다.")
    @Test
    internal fun updateStockDataTest() {

        // given AEVA 주식의 마지막 주가가 12.36인 데이터를 업데이트 해야한다는 명령
        val symbol = "AEVA"
        val command = Commander.UpdateStockData
        val lastPrice = 12.36
        val data = arrayOf(StockData(StockSymbol(symbol, "Aeva Technologies Inc", StockMarket("NYSE", "US")),
            lastPrice, 0.04, 0.32))

        // when 주식 데이터 업데이트 후 다시 조회했을 때
        commandMappingService.execute(command, data)
        val result = testStockDataRepository.findBySymbolName(symbol).lastPrice

        // then 그 값이 12.36이 맞다면 테스트 성공
        val expectedLastPrice = 12.36
        assertThat(result).isEqualTo(expectedLastPrice)
    }
}