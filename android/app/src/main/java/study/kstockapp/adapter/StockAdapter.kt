package study.kstockapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import study.kstockapp.R
import study.kstockapp.databinding.CardLayoutBinding
import study.kstockapp.domain.StockData


class StockAdapter(
    private var items: List<StockData>,
    private val clickListener: (stockData: StockData) -> Unit
) :
    RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    class StockViewHolder(val binding: CardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)
        val viewHolder = StockViewHolder(
            CardLayoutBinding.bind(view)
        )
        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder

    }

    fun add(data: StockData) {
        items = ArrayList()
        (items as ArrayList<StockData>).add(data)
        notifyDataSetChanged()
    }

    fun addAll(data: List<StockData>) {
        items = ArrayList()
        (items as ArrayList<StockData>).addAll(data)
        notifyDataSetChanged()
    }

    fun updateRecyclerView(mItems: List<StockData>) {
        items = mItems
        notifyDataSetChanged()
    }

    fun resetRecyclerView(){
        items = emptyList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.binding.stockdata = items[position]
    }
}
