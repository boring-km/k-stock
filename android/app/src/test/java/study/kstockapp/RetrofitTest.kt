package study.kstockapp

import org.awaitility.kotlin.await
import org.awaitility.kotlin.untilNotNull
import org.junit.Test
import study.kstockapp.network.KStockService
import study.kstockapp.network.RetrofitClient
import study.kstockapp.service.DaggerKStockServiceComponent

class RetrofitTest {

    private val service = RetrofitClient.getInstance().create(KStockService::class.java)
    private val stockService = DaggerKStockServiceComponent.create().getServiceImpl()

    @Test
    fun 주식_거래소_목록_불러오기_테스트() {

        // given
        val market = "nasd"
        val index = 1

        // when
        val result = stockService.getStockListByMarketWithIndex(service, market, index)
        await untilNotNull { result }

        // then
        assert(result.isNotEmpty())
    }
}