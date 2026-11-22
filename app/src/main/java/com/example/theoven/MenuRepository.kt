package com.example.theoven

object MenuRepository {
    val fullMenu = listOf(
        // Pizzas - The Oven Royalties
        MenuItem(1, "Arabic Bite", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1300, "Large" to 1800), imageRes = R.drawable.img_arabic_bite),
        MenuItem(2, "Emperor Pizza", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1550, "Large" to 2050), imageRes = R.drawable.img_emperor_pizza),
        MenuItem(3, "The Oven Special Stuff", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1550, "Large" to 2150), imageRes = R.drawable.img_emperor_pizza),
        MenuItem(4, "Pizza Pasta", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1500, "Large" to 2050), imageRes = R.drawable.img_creamy_pizza),
        MenuItem(5, "Crown Crust", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1200, "Large" to 1550), "Signature pizza with a crust made of cheese-filled crowns", imageRes = R.drawable.img_crown_crust),
        MenuItem(6, "Special Extreme Plus", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 850, "Medium" to 1550, "Large" to 2050), imageRes = R.drawable.img_special_extreme),
        MenuItem(7, "Shawarma Pizza", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 700, "Medium" to 1050, "Large" to 1400), imageRes = R.drawable.img_chicken_tikka),
        MenuItem(8, "Creamy Pizza", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 700, "Medium" to 1100, "Large" to 1350), "Rich creamy sauce with tender chicken malai boti", imageRes = R.drawable.img_creamy_pizza),
        MenuItem(9, "Special Fajita", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 650, "Medium" to 950, "Large" to 1150), imageRes = R.drawable.img_chicken_tikka),
        MenuItem(10, "Chicken Tikka", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 650, "Medium" to 950, "Large" to 1150), imageRes = R.drawable.img_chicken_tikka),
        MenuItem(11, "Cheese Lover", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 450, "Medium" to 800, "Large" to 950), "Without chicken", imageRes = R.drawable.img_creamy_pizza),
        MenuItem(12, "Special Extreme", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 750, "Medium" to 1200, "Large" to 1550), imageRes = R.drawable.img_special_extreme),
        MenuItem(50, "Extra Large Pizza", FoodCategory.ROYALTY_PIZZA, mapOf("Regular Flavour" to 2100, "Special Flavour" to 2700), "16 inch extra large pizza - Tikka, Fajita, Creamy or Extreme", imageRes = R.drawable.img_emperor_pizza),

        // Burgers & More
        MenuItem(13, "Zinger Burger", FoodCategory.BURGERS, mapOf("Standard" to 420), "Crispy fried chicken fillet with spicy mayo and lettuce", imageRes = R.drawable.img_zinger_burger),
        MenuItem(14, "Chicken Burger", FoodCategory.BURGERS, mapOf("Standard" to 350), imageRes = R.drawable.img_chicken_burger),
        MenuItem(15, "Tower Burger", FoodCategory.BURGERS, mapOf("Standard" to 700), imageRes = R.drawable.img_tower_burger),
        MenuItem(16, "Chicken & Chapli Burger", FoodCategory.BURGERS, mapOf("Standard" to 450), imageRes = R.drawable.img_chicken_burger),
        MenuItem(17, "Chapli Burger", FoodCategory.BURGERS, mapOf("Standard" to 350), imageRes = R.drawable.img_emperor_burger),
        MenuItem(18, "Grill Burger", FoodCategory.BURGERS, mapOf("Standard" to 600), imageRes = R.drawable.img_chicken_burger),
        MenuItem(19, "Oven Special Grill Burger", FoodCategory.BURGERS, mapOf("Standard" to 700), imageRes = R.drawable.img_tower_burger),
        MenuItem(20, "Emperor Burger", FoodCategory.BURGERS, mapOf("Standard" to 800), "Double patty giant burger with extra cheese", imageRes = R.drawable.img_emperor_burger),

        // Wraps & Sandwiches
        MenuItem(21, "Tortilla Grill Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 550), imageRes = R.drawable.img_grill_wrap),
        MenuItem(22, "Special Behari Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 650), "Spicy chicken behari boti rolled in a fresh paratha", imageRes = R.drawable.img_behari_wrap),
        MenuItem(23, "Special Emperor Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 650), imageRes = R.drawable.img_grill_wrap),
        MenuItem(24, "Grill Sandwich", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 700), imageRes = R.drawable.img_pan_sandwich),
        MenuItem(25, "Paratha Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 400), imageRes = R.drawable.img_behari_wrap),
        MenuItem(26, "Twister Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 400), imageRes = R.drawable.img_grill_wrap),
        MenuItem(27, "Pan Sandwich", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 700), imageRes = R.drawable.img_pan_sandwich),
        MenuItem(28, "Chicken Cheese", FoodCategory.WRAPS_SANDWICHES, mapOf("Small" to 550, "Large" to 800), "Cheesy chicken calzone", imageRes = R.drawable.img_pizza_paratha),
        MenuItem(29, "Chicken Crunchy", FoodCategory.WRAPS_SANDWICHES, mapOf("Small" to 550, "Large" to 800), "Crunchy chicken calzone", imageRes = R.drawable.img_pizza_paratha),
        MenuItem(52, "Pizza Paratha", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 450), "The Oven specialty fusion: pizza toppings inside a crispy paratha", imageRes = R.drawable.img_pizza_paratha),

        // Shawarma
        MenuItem(30, "Plain Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 250), imageRes = R.drawable.img_behari_wrap),
        MenuItem(31, "Chicken Cheese Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 320), imageRes = R.drawable.img_grill_wrap),
        MenuItem(32, "Zinger Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 380), imageRes = R.drawable.img_behari_wrap),
        MenuItem(33, "Malai Boti Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 300), imageRes = R.drawable.img_grill_wrap),
        MenuItem(34, "Grill Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 300), imageRes = R.drawable.img_grill_wrap),
        MenuItem(35, "Shawarma Platter", FoodCategory.SHAWARMA, mapOf("Standard" to 550), imageRes = R.drawable.img_shawarma_platter),

        // Fries & Sides
        MenuItem(36, "Loaded Fries", FoodCategory.FRIES_SIDES, mapOf("Small" to 500, "Medium" to 750, "Large" to 1050), imageRes = R.drawable.img_loaded_fries),
        MenuItem(37, "Regular Fries", FoodCategory.FRIES_SIDES, mapOf("Regular" to 250), imageRes = R.drawable.img_loaded_fries),
        MenuItem(38, "Large Fries", FoodCategory.FRIES_SIDES, mapOf("Large" to 400), imageRes = R.drawable.img_loaded_fries),
        MenuItem(39, "Hotshots Nuggets", FoodCategory.FRIES_SIDES, mapOf("Standard" to 400), "Crispy chicken hotshots nuggets", imageRes = R.drawable.img_hotshots),
        MenuItem(51, "Pizza Platter", FoodCategory.FRIES_SIDES, mapOf("Standard" to 1200), "Large fries + 5 HotShots + half large pizza", imageRes = R.drawable.img_shawarma_platter),

        // Wings
        MenuItem(40, "Hot Wings", FoodCategory.WINGS, mapOf("5 pc" to 380, "10 pc" to 720), imageRes = R.drawable.img_hot_wings),
        MenuItem(41, "Juicy Wings", FoodCategory.WINGS, mapOf("5 pc" to 400, "10 pc" to 800), imageRes = R.drawable.img_hot_wings),
        MenuItem(42, "Special Juicy Wings", FoodCategory.WINGS, mapOf("5 pc" to 500, "10 pc" to 850), imageRes = R.drawable.img_hot_wings),
        MenuItem(43, "Fried Chest Piece", FoodCategory.WINGS, mapOf("Standard" to 500), imageRes = R.drawable.img_hotshots),

        // Coffee & Chai
        MenuItem(44, "Cappuccino", FoodCategory.COFFEE, mapOf("Standard" to 180), imageRes = R.drawable.img_cappuccino),
        MenuItem(45, "Latte", FoodCategory.COFFEE, mapOf("Standard" to 180), imageRes = R.drawable.img_cappuccino),
        MenuItem(46, "Karak Chai", FoodCategory.COFFEE, mapOf("Standard" to 100), imageRes = R.drawable.img_cappuccino),
        MenuItem(53, "Cardamom Chai", FoodCategory.COFFEE, mapOf("Standard" to 160), imageRes = R.drawable.img_cappuccino),
        MenuItem(54, "Kashmiri Chai", FoodCategory.COFFEE, mapOf("Standard" to 130), imageRes = R.drawable.img_cappuccino),
        MenuItem(55, "Green Tea", FoodCategory.COFFEE, mapOf("Standard" to 100), imageRes = R.drawable.img_cappuccino),

        // Drinks Bar
        MenuItem(47, "Blue Lagoon", FoodCategory.DRINKS_BAR, mapOf("Standard" to 150), imageRes = R.drawable.img_blue_lagoon),
        MenuItem(48, "Cold Coffee", FoodCategory.DRINKS_BAR, mapOf("Standard" to 300), imageRes = R.drawable.img_cold_coffee),
        MenuItem(56, "Blue Sapphire", FoodCategory.DRINKS_BAR, mapOf("Standard" to 200), imageRes = R.drawable.img_blue_lagoon),
        MenuItem(57, "Mint Margrita", FoodCategory.DRINKS_BAR, mapOf("Standard" to 200), imageRes = R.drawable.img_blue_lagoon),
        MenuItem(58, "Ice Cream Shake", FoodCategory.DRINKS_BAR, mapOf("Standard" to 300), imageRes = R.drawable.img_cold_coffee),
        MenuItem(59, "Cocktail Shake", FoodCategory.DRINKS_BAR, mapOf("Standard" to 300), imageRes = R.drawable.img_cold_coffee),
        MenuItem(60, "Pina Colada", FoodCategory.DRINKS_BAR, mapOf("Standard" to 250), imageRes = R.drawable.img_blue_lagoon),
        MenuItem(61, "Cocktail Juice", FoodCategory.DRINKS_BAR, mapOf("Standard" to 200), imageRes = R.drawable.img_blue_lagoon),

        // Bottled Drinks
        MenuItem(49, "1.5L Drink", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 230), imageRes = R.drawable.img_bottled_drink),
        MenuItem(62, "Regular Drink", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 100), imageRes = R.drawable.img_bottled_drink),
        MenuItem(63, "500ml Drink", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 120), imageRes = R.drawable.img_bottled_drink),
        MenuItem(64, "1L Drink", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 180), imageRes = R.drawable.img_bottled_drink),
        MenuItem(65, "Water Large", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 120), imageRes = R.drawable.img_bottled_drink),
        MenuItem(66, "Water Small", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 70), imageRes = R.drawable.img_bottled_drink),

        // Ice Cream
        MenuItem(67, "Special Ice Cream", FoodCategory.ICE_CREAM, mapOf("Single Scoop" to 120), "Single scoop of special ice cream", imageRes = R.drawable.img_ice_cream)
    )

    val deals = listOf(
        Deal(1, "Deal 1", "2 Zinger Burgers + 500ml Drink", 890R.drawable.img_zinger_burger),
        Deal(2, "Deal 2", "4 Zinger Burgers + 1 LTR Drink", 1760R.drawable.img_zinger_burger),
        Deal(3, "Deal 3", "1 Zinger Burger + Regular Fries + 500ml Drink", 720R.drawable.img_zinger_burger),
        Deal(4, "Deal 4", "8 Zinger Burgers + 1.5 LTR Drink", 3470R.drawable.img_zinger_burger),
        Deal(5, "Deal 5", "2 Small Pizzas + 500ml Drink", 1350R.drawable.img_emperor_pizza),
        Deal(6, "Deal 6", "2 Medium Pizzas + 1 LTR Drink", 1980R.drawable.img_emperor_pizza),
        Deal(7, "Deal 7", "2 Large Pizzas + 1.5 LTR Drink", 2410R.drawable.img_emperor_pizza),
        Deal(8, "Deal 8", "1 Medium Pizza + 1 Small Pizza + 1 LTR Drink", 1680R.drawable.img_creamy_pizza),
        Deal(9, "Deal 9", "1 Large Pizza + 1 Medium Pizza + 1.5 LTR Drink", 2210R.drawable.img_emperor_pizza),
        Deal(10, "Deal 10 (NEW)", "1 Small Creamy Pizza + 1 Small Extreme Pizza + 500ml Drink", 1500R.drawable.img_creamy_pizza),
        Deal(11, "Deal 11 (NEW)", "1 Medium Creamy Pizza + 1 Medium Extreme Pizza + 1 LTR Drink", 2380R.drawable.img_special_extreme),
        Deal(12, "Deal 12 (NEW)", "1 Large Creamy Pizza + 1 Large Extreme Pizza + 1.5 LTR Drink", 3010R.drawable.img_special_extreme),
        Deal(13, "Deal 13", "2 Pizza Paratha + 500ml Drink", 1450R.drawable.img_pizza_paratha),
        Deal(14, "Deal 14", "1 Small Regular Pizza + 1 Zinger Burger + 500ml Drink", 1120R.drawable.img_zinger_burger),
        Deal(15, "Deal 15", "1 Large Pizza + 10 Hot Wings + 1.5 LTR Drink", 1980R.drawable.img_hot_wings),
        Deal(16, "Deal 16", "1 Medium Pizza + 5 Hot Wings + 1 LTR Drink", 1410R.drawable.img_hot_wings),
        Deal(17, "Deal 17", "4 Plain Chicken Shawarmas + 1 LTR Drink", 1080R.drawable.img_behari_wrap),
        Deal(18, "Deal 18", "1 Small Pizza + 1 Half Pasta + 5 Hot Wings + 1 LTR Drink", 1660R.drawable.img_emperor_pizza),
        Deal(19, "Deal 19", "1 Medium Pizza + 2 Zinger Burgers + 1 Half Pasta + 5 Hot Wings + 1.5 LTR Drink", 2830R.drawable.img_zinger_burger),
        Deal(20, "Deal 20", "1 Large Pizza (Tikka/Fajita) + 2 Zinger Burgers + 1 Full Pasta + 10 Hot Wings + 1.5 LTR Drink", 3620R.drawable.img_emperor_pizza),
        Deal(21, "Party Deal 21", "2 Large Pizzas (Tikka/Fajita) + 4 Zinger Burgers + 1 Full Pasta + 1 Half Pasta + 10 Hot Wings + 2x1L Drinks", 6230R.drawable.banner_party_deal),
        Deal(22, "Party Deal 22", "1 Crown Crust Pizza (Large) + 1 Afghani Pizza (Large) + 2 Zinger Burgers + 10 Hot Wings + 2x1L Drinks", 5290, R.drawable.banner_party_deal)
    )

    /**
     * Picks the closest generated food photo for a menu item by name.
     * Used for Firestore-seeded items, which only carry a name/category —
     * so the app shows the right dish photo instead of one shared placeholder.
     */
    fun imageFor(name: String, category: String = ""): Int {
        val n = name.lowercase()
        return when {
            "arabic" in n -> R.drawable.img_arabic_bite
            "crown" in n -> R.drawable.img_crown_crust
            "creamy" in n -> R.drawable.img_creamy_pizza
            "tikka" in n -> R.drawable.img_chicken_tikka
            "extreme" in n -> R.drawable.img_special_extreme
            "emperor" in n && "burger" in n -> R.drawable.img_emperor_burger
            "emperor" in n -> R.drawable.img_emperor_pizza
            "zinger" in n && "shawarma" in n -> R.drawable.img_behari_wrap
            "zinger" in n -> R.drawable.img_zinger_burger
            "tower" in n -> R.drawable.img_tower_burger
            "chapli" in n -> R.drawable.img_emperor_burger
            "behari" in n -> R.drawable.img_behari_wrap
            "twister" in n -> R.drawable.img_grill_wrap
            "grill wrap" in n -> R.drawable.img_grill_wrap
            "tortilla" in n -> R.drawable.img_grill_wrap
            "shawarma platter" in n -> R.drawable.img_shawarma_platter
            "shawarma" in n -> R.drawable.img_behari_wrap
            "pan sandwich" in n -> R.drawable.img_pan_sandwich
            "grill sandwich" in n -> R.drawable.img_pan_sandwich
            "paratha" in n -> R.drawable.img_pizza_paratha
            "chicken cheese" in n -> R.drawable.img_pizza_paratha
            "chicken crunchy" in n -> R.drawable.img_pizza_paratha
            "loaded fries" in n -> R.drawable.img_loaded_fries
            "fries" in n -> R.drawable.img_loaded_fries
            "hotshots" in n || "nuggets" in n -> R.drawable.img_hotshots
            "pizza platter" in n -> R.drawable.img_shawarma_platter
            "wings" in n -> R.drawable.img_hot_wings
            "chest" in n -> R.drawable.img_hotshots
            "cappuccino" in n || "latte" in n || "chai" in n || "karak" in n || "kashmiri" in n || "cardamom" in n || "green tea" in n -> R.drawable.img_cappuccino
            "blue lagoon" in n || "blue sapphire" in n || "margarita" in n || "pina" in n || "cocktail" in n -> R.drawable.img_blue_lagoon
            "cold coffee" in n || "shake" in n -> R.drawable.img_cold_coffee
            "ice cream" in n -> R.drawable.img_ice_cream
            "drink" in n || "water" in n -> R.drawable.img_bottled_drink
            "pizza" in n -> R.drawable.img_emperor_pizza
            "burger" in n -> R.drawable.img_chicken_burger
            "wrap" in n -> R.drawable.img_grill_wrap
            else -> R.drawable.img_emperor_pizza
        }
    }
}
