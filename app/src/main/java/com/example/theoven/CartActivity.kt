package com.example.theoven

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theoven.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        setupRecyclerView()
        updateBill()

        binding.toggleOrderType.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnDelivery -> {
                        binding.tilOrderLocation.hint = "Delivery Address"
                        binding.tvDeliveryFee.text = "Rs. 100"
                    }
                    R.id.btnPickup -> {
                        binding.tilOrderLocation.hint = "Pickup Branch (Mian Channu)"
                        binding.tvDeliveryFee.text = "Rs. 0"
                    }
                    R.id.btnDineIn -> {
                        binding.tilOrderLocation.hint = "Table Number"
                        binding.tvDeliveryFee.text = "Rs. 0"
                    }
                }
                updateBill()
            }
        }

        binding.btnPlaceOrder.setOnClickListener {
            if (CartManager.getItemsCount(this) > 0) {
                val subtotal = CartManager.getTotalPrice(this)
                val deliveryFee = if (binding.btnDelivery.isChecked) 100 else 0
                val total = subtotal + deliveryFee
                val location = binding.etOrderLocation.text?.toString() ?: ""

                val orderType = when {
                    binding.btnPickup.isChecked -> "Pickup"
                    binding.btnDineIn.isChecked -> "Dine-In"
                    else -> "Delivery"
                }

                val intent = Intent(this, PaymentActivity::class.java).apply {
                    putExtra("EXTRA_TOTAL", total)
                    putExtra("EXTRA_DELIVERY_FEE", deliveryFee)
                    putExtra("EXTRA_ORDER_TYPE", orderType)
                    putExtra("EXTRA_LOCATION", location)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshCart()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(CartManager.getItems(this))
        binding.rvCartItems.layoutManager = LinearLayoutManager(this)
        binding.rvCartItems.adapter = cartAdapter
    }

    private fun refreshCart() {
        cartAdapter = CartAdapter(CartManager.getItems(this))
        binding.rvCartItems.adapter = cartAdapter
        updateBill()
    }

    private fun updateBill() {
        val subtotal = CartManager.getTotalPrice(this)
        val deliveryFee = if (binding.btnDelivery.isChecked) 100 else 0
        val total = subtotal + deliveryFee

        binding.tvSubtotal.text = "Rs. $subtotal"
        binding.tvDeliveryFee.text = "Rs. $deliveryFee"
        binding.tvTotal.text = "Rs. $total"
    }
}
