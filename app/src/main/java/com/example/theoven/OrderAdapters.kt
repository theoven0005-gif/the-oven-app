package com.example.theoven

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theoven.databinding.ItemOrderBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderHistoryAdapter(
    private var orders: List<OrderModel>,
    private val onOrderClick: (OrderModel) -> Unit
) : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateOrders(newOrders: List<OrderModel>) {
        orders = newOrders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.tvOrderId.text = "Order #${order.orderId}"
        holder.binding.tvOrderDate.text = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            .format(Date(order.timestamp))
        holder.binding.tvOrderItems.text =
            "${order.items.sumOf { it.quantity }} item(s) - ${order.items.take(2).joinToString(", ") { it.name }}"
        holder.binding.tvOrderType.text = order.orderType
        holder.binding.tvOrderTotal.text = "Rs. ${order.totalAmount}"
        holder.binding.tvOrderStatus.text = when (order.status) {
            PaymentStatus.PENDING_VERIFICATION -> "Verifying Payment"
            PaymentStatus.APPROVED -> "Preparing"
            PaymentStatus.REJECTED -> "Payment Rejected"
            PaymentStatus.COD_CONFIRMED -> "Confirmed (COD)"
        }
        holder.binding.root.setOnClickListener { onOrderClick(order) }
    }

    override fun getItemCount() = orders.size
}
