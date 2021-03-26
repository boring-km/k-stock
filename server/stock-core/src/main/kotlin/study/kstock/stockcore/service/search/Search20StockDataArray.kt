package study.kstock.stockcore.service.search

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import study.kstock.stockcore.model.StockData
import study.kstock.stockcore.model.StockDataRepository
import javax.annotation.Resource

@Service
class Search20StockDataArray: SearchStockService<StockData> {

    @Resource
    private lateinit var stockDataRepository: StockDataRepository

    override fun search(vararg data: Any): MutableList<StockData> {
        val market = data[0] as String
        val start = data[1] as Int
        return stockDataRepository.getArrayOf20Stocks(market, PageRequest.of(start, 20))
    }
}