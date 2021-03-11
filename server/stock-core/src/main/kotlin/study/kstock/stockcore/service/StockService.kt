package study.kstock.stockcore.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import study.kstock.stockcore.model.StockMarket
import study.kstock.stockcore.model.StockMarketRepository
import javax.annotation.Resource


@Service
class StockService {

    @Resource
    lateinit var stockMarketRepository: StockMarketRepository

    fun getMarket(market: String): StockMarket {
        return stockMarketRepository.findByMarketName(market).orElseGet {
            throw IllegalArgumentException("없음")
        }
    }

    @Cacheable("getMarketList")
    fun getMarketList(): List<StockMarket> {
        return stockMarketRepository.findAll()
    }


}
