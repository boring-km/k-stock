package study.kstockapp

import org.junit.Test
import study.kstockapp.network.KStockService
import study.kstockapp.network.RetrofitClient
import study.kstockapp.service.KStockServiceImpl

class RetrofitTest {

    private val service = RetrofitClient.getInstance().create(KStockService::class.java)
    private val stockService = KStockServiceImpl()  // TODO 나중에 Dagger로

    @Test
    fun 주식_거래소_목록_불러오기_테스트() {

        // given
        val market = "nasd"
        val index = 1

        // when
        val result = stockService.getStockListByMarketWithIndex(service, market, index)

        // then
        assert(result.isNotEmpty())
    }
}