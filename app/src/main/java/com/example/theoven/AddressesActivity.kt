package com.example.theoven

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.theoven.databinding.ActivityAddressesBinding

class AddressesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnAddAddress.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
            Toast.makeText(this, "Add address in profile for now", Toast.LENGTH_SHORT).show()
        }
    }
}