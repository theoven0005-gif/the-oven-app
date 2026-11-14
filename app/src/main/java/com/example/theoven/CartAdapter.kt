package com.example.theoven

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theoven.databinding.ItemCartBinding
import org.json.JSONObject

data class CartItem(
    val name: String,
    val details: String,
    val unitPrice: Int,
    val quantity: Int = 1,
    val imageRes: Int = R.drawable.ic_pizza_real
) {
    fun totalPrice(): Int = unitPrice * quantity

    fun displayPrice(): String = "Rs. ${totalPrice()}"

    fun toJson(): JSONObject = JSONObject()
        .put("name", name)
        .put("details", details)
        .put("unitPrice", unitPrice)
        .put("quantity", quantity)
        .put("imageRes", imageRes)

    companion object {
        fun fromJson(o: JSONObject): CartItem = CartItem(
            name = o.optString("name"),
            details = o.optString("details"),
            unitPrice = o.optInt("unitPrice"),
            quantity = o.optInt("quantity", 1),
            imageRes = o.optInt("imageRes", R.drawable.ic_pizza_real)
        )
    }
}

class CartAdapter(private val items: List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvCartItemName.text = item.name
        holder.binding.tvCartItemDetails.text = if (item.quantity > 1)
            "${item.details} (x${item.quantity})" else item.details
        holder.binding.tvCartItemPrice.text = item.displayPrice()
        holder.binding.imgCartItem.setImageResource(item.imageRes)
    }

    override fun getItemCount() = items.size
}
