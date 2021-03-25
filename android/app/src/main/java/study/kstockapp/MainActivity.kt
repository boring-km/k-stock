package study.kstockapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import study.kstockapp.databinding.ActivityMainBinding
import study.kstockapp.databinding.CardLayoutBinding

class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        binding.spinnerStockMarket.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "시장 중 하나를 선택해주세요", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val bindViewText =
                    "You pressed position $position, Market ${binding.spinnerStockMarket.selectedItem}"
                binding.textView.text = bindViewText
            }
        }

        // 샘플 데이터 삽입
        val sampleStockData = arrayListOf<StockData>()
        for (i in 1..6) {
            sampleStockData.add(StockData("주식데이터 $i 번째", "NYSE", 30.25,0.0,0.0))
        }

        binding.recyclerviewSearchedStock.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = StockAdapter(sampleStockData) {
                    stock ->
                Toast.makeText(this@MainActivity, "$stock", Toast.LENGTH_SHORT).show()
                val intent = Intent(baseContext, StockDetail::class.java)
                intent.putExtra("obj", stock)
                startActivity(intent)
            }
        }

    }

}

class StockAdapter(val items: List<StockData>,
                   private val clickListener: (stockData: StockData) -> Unit) :
    RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    class StockViewHolder(val binding: CardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)
        val viewHolder = StockViewHolder(CardLayoutBinding.bind(view))
        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder

    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.binding.stockdata = items[position]
    }
}
