package com.example.theoven

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.theoven.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        loadCurrentData()
    }

    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val phone = binding.etPhone.text.toString()
            val email = binding.etEmail.text.toString()
            val address = binding.etAddress.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                UserManager.saveUser(this, name, phone, email, address)
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Name and Phone are required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadCurrentData() {
        binding.etName.setText(UserManager.getName(this))
        binding.etPhone.setText(UserManager.getPhone(this))
        binding.etEmail.setText(UserManager.getEmail(this))
        binding.etAddress.setText(UserManager.getAddress(this))
    }
}