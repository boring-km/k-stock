package study.kstock.stockcore.service

import org.springframework.stereotype.Service
import study.kstock.stockcore.model.Commander
import study.kstock.stockcore.service.save.StockDataSaving
import javax.annotation.Resource
import kotlin.jvm.Throws

@Service
class CommandMappingService {

    @Resource private lateinit var dataStockDataSaving: StockDataSaving
    @Resource private lateinit var symbolStockDataSaving: StockDataSaving

    @Throws(IllegalArgumentException::class)
    fun execute(command: Commander, data: Any) {
        when(command) {
            Commander.UpdateStockData -> dataStockDataSaving.save(data)
            Commander.UpdateSymbol -> symbolStockDataSaving.save(data)
            Commander.None -> throw IllegalArgumentException("잘못된 명령전달")
        }
    }
}