package study.kstock.stockcore.service.update

import org.springframework.stereotype.Service

@Service
class UpdateRealTimeDataStockData: UpdateStockDataService {
    override fun update(data: Any) {
        TODO("MariaDB에 StockSymbol 데이터를 업데이트 해준다.")
    }
}