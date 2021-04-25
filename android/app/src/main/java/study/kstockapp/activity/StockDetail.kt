package study.kstockapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import study.kstockapp.R
import study.kstockapp.databinding.StockDetailBinding
import study.kstockapp.domain.StockData

class StockDetail : AppCompatActivity() {

    companion object{
        private const val TAG = "StockDetail"
    }

    private lateinit var binding: StockDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.stock_detail
        )
        binding.detail = this

        // 원래대로라면 stockdata의 symbolId를 참고해 DB내의 stockdata를 가져왔어야 함.
        // 일단 데이터 표시를 위해 StockData를 보여줌

        val intent = intent
        val stockData = intent.getSerializableExtra("obj") as StockData

        Log.d(TAG, "stockData.stockSymbol: ${stockData.stockSymbol}")
        Log.d(TAG, "stockData.stockSymbolName: ${stockData.stockSymbol.name}")
        Log.d(TAG, "stockData.lastPrice: ${stockData.lastPrice}")
        Log.d(TAG, "stockData.priceChange: ${stockData.priceChange}")
        Log.d(TAG, "stockData.percentChange: ${stockData.percentChange}")

        binding.stockname.text = stockData.stockSymbol.toString()
        binding.stockmarketValue.text = stockData.stockSymbol.name
        binding.currentpriceValue.text = stockData.lastPrice.toPlainString()
        binding.pricechangeValue.text = stockData.priceChange.toPlainString()
        binding.percentchangeValue.text = stockData.percentChange.toPlainString()

    }
}