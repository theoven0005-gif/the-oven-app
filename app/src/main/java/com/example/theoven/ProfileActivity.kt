package com.example.theoven

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.theoven.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    override fun onResume() {
        super.onResume()
        loadUserData()
    }

    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        binding.btnMyOrders.setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
        }

        binding.btnSavedAddresses.setOnClickListener {
            startActivity(Intent(this, AddressesActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            UserManager.logout(this)
            CartManager.clearCart(this)
            try {
                FirebaseHelper.signOut()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserData() {
        binding.tvUserName.text = UserManager.getName(this)
        val phone = UserManager.getPhone(this)
        binding.tvUserPhone.text = phone.ifEmpty { "No phone number added" }
    }
}
