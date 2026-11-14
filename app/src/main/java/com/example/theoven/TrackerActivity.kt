package com.example.theoven

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.theoven.databinding.ActivityTrackerBinding

class TrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrackerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnBackHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        showOrder()
    }

    private fun showOrder() {
        val orderId = intent.getStringExtra("EXTRA_ORDER_ID")
        val order = if (orderId != null) {
            OrderManager.getOrderById(this, orderId)
        } else {
            OrderManager.getLatestOrder(this)
        }

        if (order == null) {
            binding.tvOrderNumber.text = "No orders yet"
            binding.tvStatusTitle.text = "Place your first order"
            setStepState(0)
            return
        }

        binding.tvOrderNumber.text = "Order #${order.orderId}"

        val (title, step) = when (order.status) {
            PaymentStatus.PENDING_VERIFICATION -> "Verifying Your Payment" to 1
            PaymentStatus.APPROVED -> "Preparing in The Oven" to 2
            PaymentStatus.COD_CONFIRMED -> "Order Confirmed" to 1
            PaymentStatus.REJECTED -> "Payment Rejected" to 0
        }
        binding.tvStatusTitle.text = title
        binding.tvStatusTitle.setTextColor(
            getColor(if (order.status == PaymentStatus.REJECTED) R.color.crimson_red else R.color.mint_teal)
        )
        setStepState(step)
    }

    private fun setStepState(activeStep: Int) {
        val dots = listOf(binding.step1Dot, binding.step2Dot, binding.step3Dot)
        dots.forEachIndexed { index, dot ->
            dot.setBackgroundResource(
                if (index < activeStep) R.drawable.circle_mint else R.drawable.circle_gray
            )
        }
    }
}
