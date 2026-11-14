package com.example.theoven

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theoven.databinding.ActivityMenuBinding
import com.google.android.material.tabs.TabLayout

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var adapter: MenuCategoryAdapter
    private var currentCategory: FoodCategory = FoodCategory.ROYALTY_PIZZA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        adapter = MenuCategoryAdapter(emptyList()) { item ->
            val intent = Intent(this, ItemCustomizeActivity::class.java)
            intent.putExtra("MENU_ITEM_ID", item.id)
            startActivity(intent)
        }
        binding.rvMenu.layoutManager = LinearLayoutManager(this)
        binding.rvMenu.adapter = adapter

        setupTabs()
        setupSearch()

        binding.fabFullMenu.setOnClickListener {
            startActivity(Intent(this, FullMenuActivity::class.java))
        }

        // Handle incoming category selection
        val initialCategoryName = intent.getStringExtra("CATEGORY_NAME")
        if (initialCategoryName != null) {
            val category = FoodCategory.entries.find { it.displayName == initialCategoryName }
            if (category != null) {
                currentCategory = category
                val tabIndex = FoodCategory.entries.indexOf(category)
                binding.tabLayout.post {
                    binding.tabLayout.getTabAt(tabIndex)?.select()
                    applyFilter()
                }
            } else {
                applyFilter()
            }
        } else {
            applyFilter()
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilter()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun applyFilter() {
        val query = binding.etSearch.text.toString().trim()
        val list = if (query.isEmpty()) {
            MenuRepository.fullMenu.filter { it.category == currentCategory }
        } else {
            MenuRepository.fullMenu.filter {
                it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
            }
        }
        Log.d("MenuActivity", "Showing ${list.size} items (query='$query')")
        adapter.updateItems(list)
    }

    private fun setupTabs() {
        binding.tabLayout.removeAllTabs()
        FoodCategory.entries.forEach { category ->
            val tab = binding.tabLayout.newTab().setText(category.displayName)
            binding.tabLayout.addTab(tab)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val selectedText = tab?.text?.toString()
                Log.d("MenuActivity", "Selected tab: $selectedText")
                val category = FoodCategory.entries.find { it.displayName == selectedText }
                if (category != null) {
                    currentCategory = category
                    binding.etSearch.setText("")
                    applyFilter()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}
