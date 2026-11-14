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

    // Promo banners use the real restaurant flyers
    val banners = listOf(
        Banner(1, R.drawable.menu_flyer_3),
        Banner(2, R.drawable.menu_flyer_4),
        Banner(3, R.drawable.menu_flyer_1),
        Banner(4, R.drawable.menu_flyer_2)
    )

    val categories = listOf(
        Category(1, "Royalties Pizza", R.drawable.ic_pizza_real, FoodCategory.ROYALTY_PIZZA),
        Category(2, "Burgers & More", R.drawable.ic_burger_real, FoodCategory.BURGERS),
        Category(3, "Wraps & Sandwiches", R.drawable.ic_burger, FoodCategory.WRAPS_SANDWICHES),
        Category(4, "Shawarma", R.drawable.ic_bbq, FoodCategory.SHAWARMA),
        Category(5, "Fries & Sides", R.drawable.pizza_fries, FoodCategory.FRIES_SIDES),
        Category(6, "Wings", R.drawable.bbq_placeholder, FoodCategory.WINGS),
        Category(7, "Coffee & Chai", R.drawable.banner_1, FoodCategory.COFFEE),
        Category(8, "Drinks Bar", R.drawable.banner_2, FoodCategory.DRINKS_BAR),
        Category(9, "Bottled Drinks", R.drawable.banner_2, FoodCategory.BOTTLED_DRINKS),
        Category(10, "Ice Cream", R.drawable.banner_1, FoodCategory.ICE_CREAM)
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
