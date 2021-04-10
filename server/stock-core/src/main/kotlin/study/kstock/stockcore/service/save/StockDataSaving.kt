package study.kstock.stockcore.service.save

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import study.kstock.stockcore.model.StockData
import study.kstock.stockcore.model.StockDataRepository

@Service
class StockDataSaving: StockSavingService {

    @Autowired
    private lateinit var stockDataRepository: StockDataRepository

    override fun save(data: Any) {
        @Suppress("UNCHECKED_CAST")
        stockDataRepository.saveAll(data as MutableList<StockData>)
    }
}