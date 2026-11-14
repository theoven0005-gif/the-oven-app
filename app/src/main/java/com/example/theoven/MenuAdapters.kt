package com.example.theoven

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theoven.databinding.ItemMenuRowBinding
import com.example.theoven.databinding.ItemDealBinding

class MenuCategoryAdapter(
    private var items: List<MenuItem>,
    private val onItemClick: (MenuItem) -> Unit = {}
) : RecyclerView.Adapter<MenuCategoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemMenuRowBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateItems(newItems: List<MenuItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val context = holder.binding.root.context

        holder.binding.tvMenuName.text = item.name
        holder.binding.tvMenuDescription.text = item.description
        holder.binding.tvMenuDescription.visibility = if (item.description.isEmpty()) View.GONE else View.VISIBLE
        holder.binding.imgMenu.setImageResource(item.imageRes)

        // Using the existing TableLayout but ensuring it's visible and correctly filled
        holder.binding.priceTable.removeAllViews()

        item.prices.forEach { (size, price) ->
            val priceRow = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setPadding(0, 8, 0, 8)
            }

            val sizeLabel = TextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                text = if (size == "Default" || size == "Standard") "Price" else size
                setTextColor(context.getColor(R.color.charcoal_black))
                textSize = 14f
            }

            val priceValue = TextView(context).apply {
                text = "Rs. $price"
                setTextColor(context.getColor(R.color.mint_teal))
                textSize = 15f
                setTypeface(null, Typeface.BOLD)
            }

            priceRow.addView(sizeLabel)
            priceRow.addView(priceValue)
            holder.binding.priceTable.addView(priceRow)
        }

        holder.binding.root.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount() = items.size
}

class DealsAdapter(
    private val deals: List<Deal>,
    private val onDealClick: (Deal) -> Unit = {}
) : RecyclerView.Adapter<DealsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemDealBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deal = deals[position]
        holder.binding.tvDealTitle.text = deal.title
        holder.binding.tvDealContents.text = deal.contents
        holder.binding.tvDealPrice.text = "Rs. ${deal.price}"
        holder.binding.imgDeal.setImageResource(deal.imageRes)
        holder.binding.root.setOnClickListener { onDealClick(deal) }
    }

    override fun getItemCount() = deals.size
}
