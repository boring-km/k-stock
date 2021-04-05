package study.kstock.stockcore.service.save

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import study.kstock.stockcore.model.StockSymbol
import study.kstock.stockcore.model.StockSymbolRepository

@Service
class StockSymbolSaving: StockSavingService {

    @Autowired
    private lateinit var stockSymbolRepository: StockSymbolRepository
    
    override fun save(data: Any) {
        @Suppress("UNCHECKED_CAST")
        stockSymbolRepository.saveAll(data as MutableIterable<StockSymbol>)
    }
}