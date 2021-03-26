package study.kstock.stockcore.service

import org.springframework.stereotype.Service
import study.kstock.stockcore.model.Commander
import study.kstock.stockcore.service.update.UpdateRealTimeDataStockData
import javax.annotation.Resource
import kotlin.jvm.Throws

@Service
class CommandMappingService {

    @Resource private lateinit var updateRealTimeDataStockData: UpdateRealTimeDataStockData
    @Resource private lateinit var updateSymbolStockData: UpdateRealTimeDataStockData

    @Throws(IllegalArgumentException::class)
    fun execute(command: Commander, data: Any) {
        when(command) {
            Commander.UpdateStockData -> updateRealTimeDataStockData.update(data)
            Commander.UpdateSymbol -> updateSymbolStockData.update(data)
            Commander.None -> throw IllegalArgumentException("잘못된 명령전달")
        }
    }
}