package com.example.theoven

data class Category(val id: Int, val name: String, val icon: Int, val foodCategory: FoodCategory)

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val priceLabel: String,
    val categoryId: Int,
    val imageRes: Int,
    val menuItemId: Int,
    val isFeatured: Boolean = false
)

data class Banner(val id: Int, val imageRes: Int)

object MenuData {

    // Promo banners use freshly generated food photography (not the flyers)
    val banners = listOf(
        Banner(1, R.drawable.banner_party_deal),
        Banner(2, R.drawable.banner_zinger_deal),
        Banner(3, R.drawable.banner_pizza_royalty),
        Banner(4, R.drawable.banner_drinks)
    )

    val categories = listOf(
        Category(1, "Royalties Pizza", R.drawable.img_emperor_pizza, FoodCategory.ROYALTY_PIZZA),
        Category(2, "Burgers & More", R.drawable.img_zinger_burger, FoodCategory.BURGERS),
        Category(3, "Wraps & Sandwiches", R.drawable.img_grill_wrap, FoodCategory.WRAPS_SANDWICHES),
        Category(4, "Shawarma", R.drawable.img_shawarma_platter, FoodCategory.SHAWARMA),
        Category(5, "Fries & Sides", R.drawable.img_loaded_fries, FoodCategory.FRIES_SIDES),
        Category(6, "Wings", R.drawable.img_hot_wings, FoodCategory.WINGS),
        Category(7, "Coffee & Chai", R.drawable.img_cappuccino, FoodCategory.COFFEE),
        Category(8, "Drinks Bar", R.drawable.img_blue_lagoon, FoodCategory.DRINKS_BAR),
        Category(9, "Bottled Drinks", R.drawable.img_bottled_drink, FoodCategory.BOTTLED_DRINKS),
        Category(10, "Ice Cream", R.drawable.img_ice_cream, FoodCategory.ICE_CREAM)
    )

    private val featuredIds = setOf(5, 13, 15, 20, 40, 8, 22)

    // Full real menu derived from MenuRepository so Home always matches the Menu screen
    val products: List<Product> = MenuRepository.fullMenu.map { item ->
        val (label, price) = item.prices.entries.first()
        Product(
            id = item.id,
            name = item.name,
            description = item.description.ifEmpty { item.category.displayName },
            price = price,
            priceLabel = label,
            categoryId = item.category.ordinal + 1,
            imageRes = item.imageRes,
            menuItemId = item.id,
            isFeatured = item.id in featuredIds
        )
    }
}
