package study.kstock.stockcore.model

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class StockService {

    @Resource
    lateinit var stockMarketRepository: StockMarketRepository

    val logger: Logger = LoggerFactory.getLogger(StockService::class.java)

    @Cacheable("marketName")
    fun getMarket(market: String): StockMarket {
        val result = stockMarketRepository.findByMarketName(market).orElseGet {
            throw IllegalArgumentException("없음")
        }
        return result
    }
}
