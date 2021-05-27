package study.kstockapp.service

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import study.kstockapp.adapter.StockAdapter
import study.kstockapp.databinding.ActivityMainBinding
import study.kstockapp.domain.StockData
import study.kstockapp.network.KStockService
import java.math.BigDecimal
import javax.inject.Inject

class KStockServiceImpl @Inject constructor() {

    fun searchStock(
        service: KStockService,
        searchText: String,
        binding: ActivityMainBinding
    ) {
        service.getStockBySymbol(searchText).enqueue(object : Callback<StockData> {
            override fun onResponse(
                call: Call<StockData>,
                response: Response<StockData>
            ) {
                if (response.isSuccessful) {
                    val data = response.body() as StockData
                    binding.searchedStockRecyclerView.apply {
                        (adapter as StockAdapter).add(data)
                    }
                }
            }

            override fun onFailure(call: Call<StockData>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}