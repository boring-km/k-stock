package study.kstockapp.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import study.kstockapp.domain.StockData
import study.kstockapp.domain.StockNameArray

interface KStockService {
    // 주식 데이터 주어진 인덱스로부터 20개씩 조회
    @GET("stock/list/{market}/{index}")
    fun getStockListByMarketWithIndex(@Path("market") marketString : String, @Path("index") index: Int) : Call<List<StockData>>

    // Region에 따른 거래소 목록 조회
    @GET("stock/market/list/{region}")
    fun getStockSymbolListByNation(@Path("region") regionString : String) : Call<StockData>

    // Symbol로 주식 조회
    @GET("search/{symbol}")
    fun getStockBySymbol(@Path("symbol") symbolString : String) : Call<StockData>

    @POST("search/list")
    fun getStocksBySymbols(@Body stockNames: StockNameArray): Call<List<StockData>>
}
