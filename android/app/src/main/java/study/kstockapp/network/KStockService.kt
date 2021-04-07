package study.kstockapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import study.kstockapp.domain.StockData

interface KStockService {
    @GET("stock/list/{market}/{index}")
    fun getStockListByMarketWithIndex(@Path("market") marketString : String, @Path("index") index: Int) : Call<List<StockData>>

    @GET("stock/market/list/{region}")
    fun getStockSymbolListByNation(@Path("region") regionString : String) : Call<StockData>

    @GET("search/{symbol}")
    fun getStockBySymbol(@Path("symbol") symbolString : String) : Call<StockData>
}
