package study.kstock.stockcore.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import study.kstock.stockcore.message.ApiRequest
import study.kstock.stockcore.model.*
import javax.annotation.Resource

@Service
class StockService {

    @Resource private lateinit var stockMarketRepository: StockMarketRepository
    @Resource private lateinit var stockSymbolRepository: StockSymbolRepository
    @Resource private lateinit var stockDataRepository: StockDataRepository

    val logger: Logger = LoggerFactory.getLogger(StockService::class.java)

    fun getMarketList(): MutableList<StockMarket> {
        return stockMarketRepository.findAll()
    }

    fun getStockSymbolList(): MutableList<StockSymbol> {
        return stockSymbolRepository.findAll()
    }

    fun getStockDataList(): MutableList<StockData> {
        return stockDataRepository.findAll()
    }

    fun execute(apiRequest: ApiRequest) {
        when(apiRequest.command()) {
            "saveStockIntoMyStock" -> saveStockIntoMyStock(apiRequest.data())
            else -> TODO("구현 아직 안함")
        }
    }

    fun saveStockIntoMyStock(data: Any) {
        @Suppress("UNCHECKED_CAST")
        val arrayData = data as ArrayList<String>
        val symbol = arrayData[0]
        val userId = arrayData[1]
        logger.info("전달받은 주식종목: $symbol, 유저 ID: $userId")
        // TODO: MariaDB에 데이터 insert
    }
}