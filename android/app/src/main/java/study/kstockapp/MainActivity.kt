package study.kstockapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import study.kstockapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        binding.spinnerStockMarket.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            //https://stackoverflow.com/questions/16439463/why-is-onnothingselected-method-needed-in-spinner-listener
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

    }

}

