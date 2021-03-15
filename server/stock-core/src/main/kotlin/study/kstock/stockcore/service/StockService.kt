package study.kstock.stockcore.service

import org.springframework.stereotype.Service
import study.kstock.stockcore.model.*
import javax.annotation.Resource

@Service
class StockService {

    @Resource private lateinit var stockMarketRepository: StockMarketRepository
    @Resource private lateinit var stockSymbolRepository: StockSymbolRepository
    @Resource private lateinit var stockDataRepository: StockDataRepository

    fun getMarketList(): MutableList<StockMarket> {
        return stockMarketRepository.findAll()
    }

    fun getStockSymbolList(): MutableList<StockSymbol> {
        return stockSymbolRepository.findAll()
    }

    fun getStockDataList(): MutableList<StockData> {
        return stockDataRepository.findAll()
    }

    fun execute(apiRequest: ApiRequest): Any {
        TODO("Not yet implemented")
    }
}