package study.kstockapp

import org.awaitility.kotlin.await
import org.awaitility.kotlin.until
import org.awaitility.kotlin.untilNotNull
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import study.kstockapp.domain.StockData
import study.kstockapp.network.KStockService
import study.kstockapp.network.RetrofitClient
import study.kstockapp.service.DaggerKStockServiceComponent

class RetrofitTest {

    private val service = RetrofitClient.getInstance().create(KStockService::class.java)

    @Test
    fun nasd_거래소의_주식을_1부터_가져오면_데이터가_존재한다() {

        // given
        val market = "nasd"
        val index = 1

        // when
        var resultList: ArrayList<StockData>? = null
        service.getStockListByMarketWithIndex(market, index).enqueue(object :
            Callback<List<StockData>> {
            override fun onResponse(
                call: Call<List<StockData>>,
                response: Response<List<StockData>>
            ) {
                if (response.isSuccessful) {
                    resultList = response.body() as ArrayList<StockData>
                }
            }

            override fun onFailure(call: Call<List<StockData>>, t: Throwable) {
                print("에러")
            }
        })
        await untilNotNull  { resultList }
        resultList!!.forEach { result -> println(result) }

        // then
        assert(resultList!!.isNotEmpty())
    }
}