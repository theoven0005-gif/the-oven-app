package com.example.theoven

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.theoven.databinding.ActivityDealsBinding

class DealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDealsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.rvDeals.adapter = DealsAdapter(MenuRepository.deals) { deal ->
            CartManager.addItem(
                this,
                CartItem(
                    name = deal.title,
                    details = deal.contents,
                    unitPrice = deal.price,
                    quantity = 1,
                    imageRes = deal.imageRes
                )
            )
            Toast.makeText(this, "${deal.title} added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh in case data changed
        binding.rvDeals.adapter?.notifyDataSetChanged()
    }
}
