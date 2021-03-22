package study.kstock.stockcore.service

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import study.kstock.stockcore.model.StockData
import study.kstock.stockcore.model.StockDataRepository
import study.kstock.stockcore.model.StockMarket
import study.kstock.stockcore.model.StockMarketRepository
import javax.annotation.Resource

@Service
class StockService {

    @Resource private lateinit var stockMarketRepository: StockMarketRepository
    @Resource private lateinit var stockDataRepository: StockDataRepository

    fun getArrayOf20Stocks(market: String, start: Int): MutableList<StockData> {
        return stockDataRepository.getArrayOf20Stocks(market, PageRequest.of(start, 20))
    }

    fun getMarketListBy(region: String): MutableList<StockMarket> {
        return stockMarketRepository.findByRegion(region)
    }
}