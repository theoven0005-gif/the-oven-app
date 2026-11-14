package com.example.theoven

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.theoven.databinding.ActivityItemCustomizeBinding

class ItemCustomizeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemCustomizeBinding

    private var quantity = 1
    private var productName = "Item"
    private var productImage = R.drawable.ic_pizza_real
    private var prices: Map<String, Int> = emptyMap()
    private var sizeKeys: List<String> = emptyList()
    private var isExtraLarge = false

    // Radio buttons in layout order, used to map dynamic size options
    private lateinit var sizeRadios: List<RadioButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sizeRadios = listOf(binding.rbSmall, binding.rbMedium, binding.rbLarge)

        val menuItemId = intent.getIntExtra("MENU_ITEM_ID", -1)
        val menuItem = MenuRepository.fullMenu.find { it.id == menuItemId }
        if (menuItem != null) {
            productName = menuItem.name
            prices = menuItem.prices
            productImage = menuItem.imageRes
            isExtraLarge = menuItem.name == "Extra Large Pizza"
        } else {
            // Legacy fallback (old callers passed name/price directly)
            productName = intent.getStringExtra("PRODUCT_NAME") ?: "Pizza"
            val price = intent.getIntExtra("PRODUCT_PRICE", 1199)
            prices = mapOf("Standard" to price)
            productImage = intent.getIntExtra("PRODUCT_IMAGE", R.drawable.pizza_placeholder)
        }
        sizeKeys = prices.keys.toList()

        binding.tvProductName.text = productName
        binding.imgProductHero.setImageResource(productImage)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { finish() }

        setupOptions()
        setupPriceListeners()
        setupQuantityControls()
        updatePrice()

        binding.btnAddToCart.setOnClickListener {
            val (unit, extras, sizeLabel) = currentSelection()
            val instructions = binding.etInstructions.text.toString().trim()
            val details = buildString {
                append(sizeLabel)
                if (extras.isNotEmpty()) append(", +").append(extras.joinToString(", +"))
                if (instructions.isNotEmpty()) append(" | Note: ").append(instructions)
            }
            val item = CartItem(
                name = productName,
                details = details,
                unitPrice = unit,
                quantity = quantity,
                imageRes = productImage
            )
            CartManager.addItem(this, item)

            Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun setupOptions() {
        if (isExtraLarge) {
            // Extra Large: flavour radio picks the price, no size choice
            binding.tvFlavorTitle.visibility = View.VISIBLE
            binding.rgFlavor.visibility = View.VISIBLE
            binding.rbFlavorRegular.text = "Regular Flavour (Rs. ${prices["Regular Flavour"]})"
            binding.rbFlavorSpecial.text = "Special Flavour (Rs. ${prices["Special Flavour"]})"
            binding.tvSizeTitle.visibility = View.GONE
            binding.rgSize.visibility = View.GONE
        } else {
            binding.tvFlavorTitle.visibility = View.GONE
            binding.rgFlavor.visibility = View.GONE
            if (sizeKeys.size <= 1) {
                binding.tvSizeTitle.visibility = View.GONE
                binding.rgSize.visibility = View.GONE
            } else {
                binding.tvSizeTitle.visibility = View.VISIBLE
                binding.rgSize.visibility = View.VISIBLE
                // Map known size names first, then fill remaining radios in order
                val assigned = mutableSetOf<Int>()
                val order = listOf("small" to 0, "medium" to 1, "large" to 2)
                sizeKeys.forEach { key ->
                    val lower = key.lowercase()
                    val idx = order.firstOrNull { (name, _) -> lower.contains(name) }?.second
                        ?: (0..2).firstOrNull { it !in assigned }
                    if (idx != null) {
                        assigned.add(idx)
                        sizeRadios[idx].tag = key
                        sizeRadios[idx].text = "$key - Rs. ${prices[key]}"
                        sizeRadios[idx].visibility = View.VISIBLE
                    }
                }
                sizeRadios.forEachIndexed { i, rb ->
                    if (i !in assigned) rb.visibility = View.GONE
                }
                sizeRadios.firstOrNull { it.visibility == View.VISIBLE }?.isChecked = true
            }
        }
        updateToppingLabels()
    }

    private fun toppingUnitPrice(): Int {
        val key = currentSizeKey().lowercase()
        return when {
            key.contains("small") -> 150
            key.contains("medium") -> 200
            key.contains("large") -> 250
            else -> 150
        }
    }

    private fun updateToppingLabels() {
        val p = toppingUnitPrice()
        binding.cbExtraCheese.text = "Extra Cheese (+Rs. $p)"
        binding.cbJalapenos.text = "Jalapenos (+Rs. $p)"
        binding.cbMushrooms.text = "Mushrooms (+Rs. $p)"
    }

    private fun currentSizeKey(): String {
        if (isExtraLarge) {
            return if (binding.rgFlavor.checkedRadioButtonId == R.id.rbFlavorSpecial) "Special Flavour"
            else "Regular Flavour"
        }
        if (sizeKeys.size <= 1) return sizeKeys.firstOrNull() ?: ""
        val checkedId = binding.rgSize.checkedRadioButtonId
        val rb = sizeRadios.find { it.id == checkedId && it.visibility == View.VISIBLE }
        return (rb?.tag as? String) ?: sizeKeys.firstOrNull() ?: ""
    }

    /** Returns Triple(unitPrice, extrasList, sizeLabel) */
    private fun currentSelection(): Triple<Int, List<String>, String> {
        val key = currentSizeKey()
        val base = prices[key] ?: 0
        val tp = toppingUnitPrice()
        val extras = mutableListOf<String>()
        var extraTotal = 0
        if (binding.cbExtraCheese.isChecked) { extras.add("Extra Cheese"); extraTotal += tp }
        if (binding.cbJalapenos.isChecked) { extras.add("Jalapenos"); extraTotal += tp }
        if (binding.cbMushrooms.isChecked) { extras.add("Mushrooms"); extraTotal += tp }
        val label = if (key.equals("Standard", true)) "Standard" else key
        return Triple(base + extraTotal, extras, label)
    }

    private fun setupPriceListeners() {
        binding.rgSize.setOnCheckedChangeListener { _, _ -> updatePrice() }
        binding.rgFlavor.setOnCheckedChangeListener { _, _ -> updatePrice() }
        binding.cbExtraCheese.setOnCheckedChangeListener { _, _ -> updatePrice() }
        binding.cbJalapenos.setOnCheckedChangeListener { _, _ -> updatePrice() }
        binding.cbMushrooms.setOnCheckedChangeListener { _, _ -> updatePrice() }
    }

    private fun setupQuantityControls() {
        binding.btnPlus.setOnClickListener {
            quantity++
            updateQuantityUI()
        }
        binding.btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantityUI()
            }
        }
    }

    private fun updateQuantityUI() {
        binding.tvQuantity.text = quantity.toString()
        updatePrice()
    }

    private fun updatePrice() {
        updateToppingLabels()
        val (unit, _, _) = currentSelection()
        val total = unit * quantity
        binding.btnAddToCart.text = "Add to Cart - Rs. $total"
    }
}
