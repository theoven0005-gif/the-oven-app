package com.example.theoven

enum class FoodCategory(val displayName: String) {
    ROYALTY_PIZZA("The Oven Royalties"),
    BURGERS("Burgers & More"),
    WRAPS_SANDWICHES("Wraps & Sandwiches"),
    SHAWARMA("Shawarma"),
    FRIES_SIDES("Fries & Sides"),
    WINGS("Wings & Fried Chicken"),
    COFFEE("Coffee"),
    DRINKS_BAR("Drinks Bar"),
    BOTTLED_DRINKS("Bottled Drinks"),
    ICE_CREAM("Ice Cream")
}

data class MenuItem(
    val id: Int,
    val name: String,
    val category: FoodCategory,
    val prices: Map<String, Int>,
    val description: String = "",
    val imageRes: Int = R.drawable.ic_pizza_real
)

data class Deal(
    val id: Int,
    val title: String,
    val contents: String,
    val price: Int,
    val imageRes: Int = R.drawable.crown_crust
)