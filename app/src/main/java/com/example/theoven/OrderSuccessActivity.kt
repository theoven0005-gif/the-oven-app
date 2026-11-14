package com.example.theoven

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.theoven.databinding.ActivityOrderSuccessBinding

class OrderSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getStringExtra("EXTRA_ORDER_ID") ?: ""
        val isCOD = intent.getBooleanExtra("EXTRA_IS_COD", false)

        if (orderId.isNotEmpty()) {
            binding.tvOrderId.text = "Order #$orderId"
        }

        if (isCOD) {
            binding.tvPaymentStatusNote.text = "Order confirmed with Cash on Delivery! Please keep exact change ready."
        } else {
            binding.tvPaymentStatusNote.text = "Your payment proof has been submitted to The Oven. You will receive notification updates."
        }

        binding.btnTrackOrder.setOnClickListener {
            startActivity(Intent(this, TrackerActivity::class.java))
            finish()
        }

        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
