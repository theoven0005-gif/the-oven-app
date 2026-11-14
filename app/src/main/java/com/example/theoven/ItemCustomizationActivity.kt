package com.example.theoven

import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.appbar.MaterialToolbar

class ItemCustomizationActivity : AppCompatActivity() {

    private var basePrice = 1250
    private var totalPrice = 1250

    private lateinit var rgSize: RadioGroup
    private lateinit var cbExtraCheese: CheckBox
    private lateinit var cbMushrooms: CheckBox
    private lateinit var cbOlives: CheckBox
    private lateinit var cbOnions: CheckBox
    private lateinit var btnAddToCart: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_customization)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        rgSize = findViewById(R.id.rgSize)
        cbExtraCheese = findViewById(R.id.cbExtraCheese)
        cbMushrooms = findViewById(R.id.cbMushrooms)
        cbOlives = findViewById(R.id.cbOlives)
        cbOnions = findViewById(R.id.cbOnions)
        btnAddToCart = findViewById(R.id.btnAddToCart)

        setupListeners()
        updatePrice()
    }

    private fun setupListeners() {
        rgSize.setOnCheckedChangeListener { _, checkedId ->
            basePrice = when (checkedId) {
                R.id.rbSmall -> 1000
                R.id.rbMedium -> 1250
                R.id.rbLarge -> 1500
                else -> 1250
            }
            updatePrice()
        }

        cbExtraCheese.setOnCheckedChangeListener { _, _ -> updatePrice() }
        cbMushrooms.setOnCheckedChangeListener { _, _ -> updatePrice() }
        cbOlives.setOnCheckedChangeListener { _, _ -> updatePrice() }
        cbOnions.setOnCheckedChangeListener { _, _ -> updatePrice() }
    }

    private fun updatePrice() {
        totalPrice = basePrice
        
        if (cbExtraCheese.isChecked) totalPrice += 150
        if (cbMushrooms.isChecked) totalPrice += 100
        if (cbOlives.isChecked) totalPrice += 80
        if (cbOnions.isChecked) totalPrice += 50
        
        btnAddToCart.text = "Add to Cart - Rs. $totalPrice"
    }
}