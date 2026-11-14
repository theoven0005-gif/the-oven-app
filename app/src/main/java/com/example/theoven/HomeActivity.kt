package com.example.theoven

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.example.theoven.databinding.ActivityHomeBinding
import com.google.firebase.firestore.ListenerRegistration
import java.util.Calendar

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    // Menu items currently shown; refreshed from Firestore when available,
    // otherwise the bundled local menu (MenuData.products).
    private var products: List<Product> = MenuData.products
    private var menuListener: ListenerRegistration? = null

    override fun onResume() {
        super.onResume()
        updateCartFab()
    }

    private fun updateCartFab() {
        val count = CartManager.getItemsCount(this)
        val total = CartManager.getTotalPrice(this)
        if (count > 0) {
            binding.fabCart.text = getString(R.string.view_cart, count, total)
            binding.fabCart.show()
            binding.fabCart.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).withEndAction {
                binding.fabCart.animate().scaleX(1.0f).scaleY(1.0f).start()
            }.start()
        } else {
            binding.fabCart.hide()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupGreeting()
        setupCategories()
        setupFeatured()
        setupPopularItems()
        setupBottomNav()
        setupBanners()
        setupFab()
        setupSearchAndProfile()
        setupFilters()
        setupScrollListener()
        startMenuListener()
        refreshFcmToken()
    }

    /**
     * Listens for menu changes from Firestore (managed in the admin panel).
     * Falls back to the bundled local menu when Firestore is unreachable
     * or nothing is seeded yet — the home screen never goes blank.
     */
    private fun startMenuListener() {
        try {
            menuListener = FirestoreMenuRepository.listenToMenuItems(
                onUpdate = { remoteProducts ->
                    products = remoteProducts
                    setupFeatured()
                    setupPopularItems()
                    setupFilters()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /** Best-effort FCM token sync so the admin panel can reach this device. */
    private fun refreshFcmToken() {
        try {
            if (FirebaseHelper.isSignedIn()) FCMTokenManager.refreshAndSave()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        FirestoreMenuRepository.stopListening(menuListener)
        super.onDestroy()
    }

    private fun setupGreeting() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greeting = when (hour) {
            in 0..11 -> getString(R.string.good_morning)
            in 12..15 -> getString(R.string.good_afternoon)
            else -> getString(R.string.good_evening)
        }
        binding.tvHomeGreeting.text = getString(R.string.home_greeting_format, greeting)
    }

    private fun setupScrollListener() {
        binding.mainContentScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            val alpha = 1.0f - (scrollY.toFloat() / 400.0f).coerceIn(0.0f, 1.0f)
            binding.tvHomeGreeting.alpha = alpha
            binding.tvHomeGreeting.translationY = scrollY.toFloat() * 0.3f
        })
    }

    private fun setupSearchAndProfile() {
        binding.toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
        
        binding.cardSearch.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        binding.cardProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.tvSeeAllCategories.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        binding.tvSeeAllFeatured.setOnClickListener {
            startActivity(Intent(this, DealsActivity::class.java))
        }
    }

    private fun setupFab() {
        binding.fabCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun setupBanners() {
        binding.bannerPager.adapter = BannerAdapter(MenuData.banners)
        binding.bannerPager.setPageTransformer { page, position ->
            page.alpha = 0.5f + (1 - Math.abs(position))
            page.scaleY = 0.85f + (1 - Math.abs(position)) * 0.15f
        }
    }

    private fun setupCategories() {
        binding.rvCategories.adapter = CategoryAdapter(MenuData.categories) { category ->
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("CATEGORY_NAME", category.foodCategory.displayName)
            startActivity(intent)
        }
    }

    private fun setupFeatured() {
        val featured = products.filter { it.isFeatured }
        binding.rvFeatured.adapter = FeaturedAdapter(featured) { product ->
            openProductDetail(product)
        }
    }

    private fun setupPopularItems() {
        binding.rvProducts.adapter = ProductAdapter(products) { product ->
            openProductDetail(product)
        }
    }

    private fun setupFilters() {
        binding.chipBestsellers.setOnClickListener {
            val featured = products.filter { it.isFeatured }
            binding.rvProducts.adapter = ProductAdapter(featured) { product ->
                openProductDetail(product)
            }
        }

        binding.chipUnder500.setOnClickListener {
            val under500 = products.filter { it.price < 500 }
            binding.rvProducts.adapter = ProductAdapter(under500) { product ->
                openProductDetail(product)
            }
        }

        binding.chipSpicy.setOnClickListener {
            val spicy = products.filter { it.name.contains("Flame", true) || it.name.contains("Extreme", true) }
            binding.rvProducts.adapter = ProductAdapter(spicy) { product ->
                openProductDetail(product)
            }
        }
    }

    private fun openProductDetail(product: Product) {
        val intent = Intent(this, ItemCustomizeActivity::class.java)
        intent.putExtra("MENU_ITEM_ID", product.menuItemId)
        startActivity(intent)
    }

    private fun setupBottomNav() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.nav_tracker -> {
                    startActivity(Intent(this, TrackerActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}