package com.example.theoven

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.theoven.databinding.ActivityOrdersBinding
import com.google.firebase.firestore.ListenerRegistration

class OrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrdersBinding
    private lateinit var adapter: OrderHistoryAdapter
    private var ordersListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        adapter = OrderHistoryAdapter(emptyList()) { order ->
            val intent = Intent(this, TrackerActivity::class.java)
            intent.putExtra("EXTRA_ORDER_ID", order.orderId)
            startActivity(intent)
        }
        binding.rvOrders.adapter = adapter

        binding.btnExplore.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        startRealtimeOrders()
    }

    /**
     * Realtime Firestore listener for the signed-in user's orders.
     * Remote orders are merged into the local OrderManager cache
     * (skipping ones already present), so status changes made in the
     * admin panel appear live. When not signed in or offline, the
     * local cache is used as-is — the screen never breaks.
     */
    private fun startRealtimeOrders() {
        try {
            ordersListener = FirestoreOrderRepository.listenToUserOrders(
                onUpdate = { remoteOrders ->
                    var changed = false
                    remoteOrders.forEach { remote ->
                        if (OrderManager.getOrderById(this, remote.orderId) == null) {
                            OrderManager.addOrder(this, remote)
                            changed = true
                        } else {
                            // Refresh status of orders we already know about.
                            val updated = OrderManager.updateOrderStatus(this, remote.orderId, remote.status)
                            if (updated != null) changed = true
                        }
                    }
                    if (changed) refreshOrders()
                },
                onError = { e ->
                    // Silent-ish: local orders still show; only toast on real errors.
                    if (binding.rvOrders.visibility != View.VISIBLE) {
                        Toast.makeText(this, "Couldn't load orders: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshOrders()
    }

    override fun onDestroy() {
        FirestoreOrderRepository.stopListening(ordersListener)
        super.onDestroy()
    }

    private fun refreshOrders() {
        val orders = OrderManager.getOrders(this)
        adapter.updateOrders(orders)
        val empty = orders.isEmpty()
        binding.layoutEmpty.visibility = if (empty) View.VISIBLE else View.GONE
        binding.rvOrders.visibility = if (empty) View.GONE else View.VISIBLE
    }
}
