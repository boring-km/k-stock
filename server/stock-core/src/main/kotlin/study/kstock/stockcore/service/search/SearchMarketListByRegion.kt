package study.kstock.stockcore.service.search

import org.springframework.stereotype.Service
import study.kstock.stockcore.model.StockMarket
import study.kstock.stockcore.model.StockMarketRepository
import javax.annotation.Resource

@Service
class SearchMarketListByRegion: SearchStockService<StockMarket> {
    @Resource
    private lateinit var stockMarketRepository: StockMarketRepository

    override fun search(vararg data: Any): MutableList<StockMarket> {
        val region = data[0] as String
        return stockMarketRepository.findByRegion(region)
    }
}