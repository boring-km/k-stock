package study.kstock.stockcore.model

import org.springframework.stereotype.Service
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

    fun getMarketList(): List<StockMarket> {
        return stockMarketRepository.findAll()
    }
}
