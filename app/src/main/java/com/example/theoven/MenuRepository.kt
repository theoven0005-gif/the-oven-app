package com.example.theoven

object MenuRepository {
    val fullMenu = listOf(
        // Pizzas - The Oven Royalties
        MenuItem(1, "Arabic Bite", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1300, "Large" to 1800), imageRes = R.drawable.img_arabic_bite),
        MenuItem(2, "Emperor Pizza", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1550, "Large" to 2050), imageRes = R.drawable.img_emperor_pizza),
        MenuItem(3, "The Oven Special Stuff", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1550, "Large" to 2150), imageRes = R.drawable.img_oven_special_stuff),
        MenuItem(4, "Pizza Pasta", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1500, "Large" to 2050), imageRes = R.drawable.img_pizza_pasta),
        MenuItem(5, "Crown Crust", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1200, "Large" to 1550), "Signature pizza with a crust made of cheese-filled crowns", imageRes = R.drawable.img_crown_crust),
        MenuItem(6, "Special Extreme Plus", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 850, "Medium" to 1550, "Large" to 2050), imageRes = R.drawable.img_special_extreme_plus),
        MenuItem(7, "Shawarma Pizza", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 700, "Medium" to 1050, "Large" to 1400), imageRes = R.drawable.img_shawarma_pizza),
        MenuItem(8, "Creamy Pizza", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 700, "Medium" to 1100, "Large" to 1350), "Rich creamy sauce with tender chicken malai boti", imageRes = R.drawable.img_creamy_pizza),
        MenuItem(9, "Special Fajita", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 650, "Medium" to 950, "Large" to 1150), imageRes = R.drawable.img_special_fajita),
        MenuItem(10, "Chicken Tikka", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 650, "Medium" to 950, "Large" to 1150), imageRes = R.drawable.img_chicken_tikka),
        MenuItem(11, "Cheese Lover", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 450, "Medium" to 800, "Large" to 950), "Without chicken", imageRes = R.drawable.img_cheese_lover),
        MenuItem(12, "Special Extreme", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 750, "Medium" to 1200, "Large" to 1550), imageRes = R.drawable.img_special_extreme),
        MenuItem(50, "Extra Large Pizza", FoodCategory.ROYALTY_PIZZA, mapOf("Regular Flavour" to 2100, "Special Flavour" to 2700), "16 inch extra large pizza - Tikka, Fajita, Creamy or Extreme", imageRes = R.drawable.img_extra_large_pizza),

        // Burgers & More
        MenuItem(13, "Zinger Burger", FoodCategory.BURGERS, mapOf("Standard" to 420), "Crispy fried chicken fillet with spicy mayo and lettuce", imageRes = R.drawable.img_zinger_burger),
        MenuItem(14, "Chicken Burger", FoodCategory.BURGERS, mapOf("Standard" to 350), imageRes = R.drawable.img_chicken_burger),
        MenuItem(15, "Tower Burger", FoodCategory.BURGERS, mapOf("Standard" to 700), imageRes = R.drawable.img_tower_burger),
        MenuItem(16, "Chicken & Chapli Burger", FoodCategory.BURGERS, mapOf("Standard" to 450), imageRes = R.drawable.img_chicken_chapli_burger),
        MenuItem(17, "Chapli Burger", FoodCategory.BURGERS, mapOf("Standard" to 350), imageRes = R.drawable.img_chapli_burger),
        MenuItem(18, "Grill Burger", FoodCategory.BURGERS, mapOf("Standard" to 600), imageRes = R.drawable.img_grill_burger),
        MenuItem(19, "Oven Special Grill Burger", FoodCategory.BURGERS, mapOf("Standard" to 700), imageRes = R.drawable.img_oven_special_grill_burger),
        MenuItem(20, "Emperor Burger", FoodCategory.BURGERS, mapOf("Standard" to 800), "Double patty giant burger with extra cheese", imageRes = R.drawable.img_emperor_burger),

        // Wraps & Sandwiches
        MenuItem(21, "Tortilla Grill Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 550), imageRes = R.drawable.img_grill_wrap),
        MenuItem(22, "Special Behari Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 650), "Spicy chicken behari boti rolled in a fresh paratha", imageRes = R.drawable.img_behari_wrap),
        MenuItem(23, "Special Emperor Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 650), imageRes = R.drawable.img_emperor_wrap),
        MenuItem(24, "Grill Sandwich", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 700), imageRes = R.drawable.img_grill_sandwich),
        MenuItem(25, "Paratha Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 400), imageRes = R.drawable.img_paratha_wrap),
        MenuItem(26, "Twister Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 400), imageRes = R.drawable.img_twister_wrap),
        MenuItem(27, "Pan Sandwich", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 700), imageRes = R.drawable.img_pan_sandwich),
        MenuItem(28, "Chicken Cheese", FoodCategory.WRAPS_SANDWICHES, mapOf("Small" to 550, "Large" to 800), "Cheesy chicken calzone", imageRes = R.drawable.img_chicken_cheese_calzone),
        MenuItem(29, "Chicken Crunchy", FoodCategory.WRAPS_SANDWICHES, mapOf("Small" to 550, "Large" to 800), "Crunchy chicken calzone", imageRes = R.drawable.img_chicken_crunchy_calzone),
        MenuItem(52, "Pizza Paratha", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 450), "The Oven specialty fusion: pizza toppings inside a crispy paratha", imageRes = R.drawable.img_pizza_paratha),

        // Shawarma
        MenuItem(30, "Plain Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 250), imageRes = R.drawable.img_plain_shawarma),
        MenuItem(31, "Chicken Cheese Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 320), imageRes = R.drawable.img_chicken_cheese_shawarma),
        MenuItem(32, "Zinger Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 380), imageRes = R.drawable.img_zinger_shawarma),
        MenuItem(33, "Malai Boti Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 300), imageRes = R.drawable.img_malai_boti_shawarma),
        MenuItem(34, "Grill Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 300), imageRes = R.drawable.img_grill_shawarma),
        MenuItem(35, "Shawarma Platter", FoodCategory.SHAWARMA, mapOf("Standard" to 550), imageRes = R.drawable.img_shawarma_platter),

        // Fries & Sides
        MenuItem(36, "Loaded Fries", FoodCategory.FRIES_SIDES, mapOf("Small" to 500, "Medium" to 750, "Large" to 1050), imageRes = R.drawable.img_loaded_fries),
        MenuItem(37, "Regular Fries", FoodCategory.FRIES_SIDES, mapOf("Regular" to 250), imageRes = R.drawable.img_regular_fries),
        MenuItem(38, "Large Fries", FoodCategory.FRIES_SIDES, mapOf("Large" to 400), imageRes = R.drawable.img_large_fries),
        MenuItem(39, "Hotshots Nuggets", FoodCategory.FRIES_SIDES, mapOf("Standard" to 400), "Crispy chicken hotshots nuggets", imageRes = R.drawable.img_hotshots),
        MenuItem(51, "Pizza Platter", FoodCategory.FRIES_SIDES, mapOf("Standard" to 1200), "Large fries + 5 HotShots + half large pizza", imageRes = R.drawable.img_pizza_platter),

        // Wings
        MenuItem(40, "Hot Wings", FoodCategory.WINGS, mapOf("5 pc" to 380, "10 pc" to 720), imageRes = R.drawable.img_hot_wings),
        MenuItem(41, "Juicy Wings", FoodCategory.WINGS, mapOf("5 pc" to 400, "10 pc" to 800), imageRes = R.drawable.img_juicy_wings),
        MenuItem(42, "Special Juicy Wings", FoodCategory.WINGS, mapOf("5 pc" to 500, "10 pc" to 850), imageRes = R.drawable.img_special_juicy_wings),
        MenuItem(43, "Fried Chest Piece", FoodCategory.WINGS, mapOf("Standard" to 500), imageRes = R.drawable.img_fried_chest),

        // Coffee & Chai
        MenuItem(44, "Cappuccino", FoodCategory.COFFEE, mapOf("Standard" to 180), imageRes = R.drawable.img_cappuccino),
        MenuItem(45, "Latte", FoodCategory.COFFEE, mapOf("Standard" to 180), imageRes = R.drawable.img_latte),
        MenuItem(46, "Karak Chai", FoodCategory.COFFEE, mapOf("Standard" to 100), imageRes = R.drawable.img_karak_chai),
        MenuItem(53, "Cardamom Chai", FoodCategory.COFFEE, mapOf("Standard" to 160), imageRes = R.drawable.img_cardamom_chai),
        MenuItem(54, "Kashmiri Chai", FoodCategory.COFFEE, mapOf("Standard" to 130), imageRes = R.drawable.img_kashmiri_chai),
        MenuItem(55, "Green Tea", FoodCategory.COFFEE, mapOf("Standard" to 100), imageRes = R.drawable.img_green_tea),

        // Drinks Bar
        MenuItem(47, "Blue Lagoon", FoodCategory.DRINKS_BAR, mapOf("Standard" to 150), imageRes = R.drawable.img_blue_lagoon),
        MenuItem(48, "Cold Coffee", FoodCategory.DRINKS_BAR, mapOf("Standard" to 300), imageRes = R.drawable.img_cold_coffee),
        MenuItem(56, "Blue Sapphire", FoodCategory.DRINKS_BAR, mapOf("Standard" to 200), imageRes = R.drawable.img_blue_sapphire),
        MenuItem(57, "Mint Margrita", FoodCategory.DRINKS_BAR, mapOf("Standard" to 200), imageRes = R.drawable.img_mint_margarita),
        MenuItem(58, "Ice Cream Shake", FoodCategory.DRINKS_BAR, mapOf("Standard" to 300), imageRes = R.drawable.img_ice_cream_shake),
        MenuItem(59, "Cocktail Shake", FoodCategory.DRINKS_BAR, mapOf("Standard" to 300), imageRes = R.drawable.img_cocktail_shake),
        MenuItem(60, "Pina Colada", FoodCategory.DRINKS_BAR, mapOf("Standard" to 250), imageRes = R.drawable.img_pina_colada),
        MenuItem(61, "Cocktail Juice", FoodCategory.DRINKS_BAR, mapOf("Standard" to 200), imageRes = R.drawable.img_cocktail_juice),

        // Bottled Drinks
        MenuItem(49, "1.5L Drink", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 230), imageRes = R.drawable.img_bottled_drink),
        MenuItem(62, "Regular Drink", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 100), imageRes = R.drawable.img_regular_drink),
        MenuItem(63, "500ml Drink", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 120), imageRes = R.drawable.img_drink_500ml),
        MenuItem(64, "1L Drink", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 180), imageRes = R.drawable.img_drink_1l),
        MenuItem(65, "Water Large", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 120), imageRes = R.drawable.img_water_large),
        MenuItem(66, "Water Small", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 70), imageRes = R.drawable.img_water_small),

        // Ice Cream
        MenuItem(67, "Special Ice Cream", FoodCategory.ICE_CREAM, mapOf("Single Scoop" to 120), "Single scoop of special ice cream", imageRes = R.drawable.img_ice_cream)
    )

    val deals = listOf(
        Deal(1, "Deal 1", "2 Zinger Burgers + 500ml Drink", 890, R.drawable.img_deal_1),
        Deal(2, "Deal 2", "4 Zinger Burgers + 1 LTR Drink", 1760, R.drawable.img_deal_2),
        Deal(3, "Deal 3", "1 Zinger Burger + Regular Fries + 500ml Drink", 720, R.drawable.img_deal_3),
        Deal(4, "Deal 4", "8 Zinger Burgers + 1.5 LTR Drink", 3470, R.drawable.img_deal_4),
        Deal(5, "Deal 5", "2 Small Pizzas + 500ml Drink", 1350, R.drawable.img_deal_5),
        Deal(6, "Deal 6", "2 Medium Pizzas + 1 LTR Drink", 1980, R.drawable.img_deal_6),
        Deal(7, "Deal 7", "2 Large Pizzas + 1.5 LTR Drink", 2410, R.drawable.img_deal_7),
        Deal(8, "Deal 8", "1 Medium Pizza + 1 Small Pizza + 1 LTR Drink", 1680, R.drawable.img_deal_8),
        Deal(9, "Deal 9", "1 Large Pizza + 1 Medium Pizza + 1.5 LTR Drink", 2210, R.drawable.img_deal_9),
        Deal(10, "Deal 10 (NEW)", "1 Small Creamy Pizza + 1 Small Extreme Pizza + 500ml Drink", 1500, R.drawable.img_deal_10),
        Deal(11, "Deal 11 (NEW)", "1 Medium Creamy Pizza + 1 Medium Extreme Pizza + 1 LTR Drink", 2380, R.drawable.img_deal_11),
        Deal(12, "Deal 12 (NEW)", "1 Large Creamy Pizza + 1 Large Extreme Pizza + 1.5 LTR Drink", 3010, R.drawable.img_deal_12),
        Deal(13, "Deal 13", "2 Pizza Paratha + 500ml Drink", 1450, R.drawable.img_deal_13),
        Deal(14, "Deal 14", "1 Small Regular Pizza + 1 Zinger Burger + 500ml Drink", 1120, R.drawable.img_deal_14),
        Deal(15, "Deal 15", "1 Large Pizza + 10 Hot Wings + 1.5 LTR Drink", 1980, R.drawable.img_deal_15),
        Deal(16, "Deal 16", "1 Medium Pizza + 5 Hot Wings + 1 LTR Drink", 1410, R.drawable.img_deal_16),
        Deal(17, "Deal 17", "4 Plain Chicken Shawarmas + 1 LTR Drink", 1080, R.drawable.img_deal_17),
        Deal(18, "Deal 18", "1 Small Pizza + 1 Half Pasta + 5 Hot Wings + 1 LTR Drink", 1660, R.drawable.img_deal_18),
        Deal(19, "Deal 19", "1 Medium Pizza + 2 Zinger Burgers + 1 Half Pasta + 5 Hot Wings + 1.5 LTR Drink", 2830, R.drawable.img_deal_19),
        Deal(20, "Deal 20", "1 Large Pizza (Tikka/Fajita) + 2 Zinger Burgers + 1 Full Pasta + 10 Hot Wings + 1.5 LTR Drink", 3620, R.drawable.img_deal_20),
        Deal(21, "Party Deal 21", "2 Large Pizzas (Tikka/Fajita) + 4 Zinger Burgers + 1 Full Pasta + 1 Half Pasta + 10 Hot Wings + 2x1L Drinks", 6230, R.drawable.img_deal_21),
        Deal(22, "Party Deal 22", "1 Crown Crust Pizza (Large) + 1 Afghani Pizza (Large) + 2 Zinger Burgers + 10 Hot Wings + 2x1L Drinks", 5290, R.drawable.img_deal_22)
    )

    /**
     * Picks the closest generated food photo for a menu item by name.
     * Used for Firestore-seeded items, which only carry a name/category —
     * so the app shows the right dish instead of one shared placeholder.
     * Specific names are checked before generic ones (first match wins).
     */
    fun imageFor(name: String, category: String = ""): Int {
        val n = name.lowercase()
        return when {
            "arabic" in n -> R.drawable.img_arabic_bite
            "crown" in n -> R.drawable.img_crown_crust
            "special stuff" in n -> R.drawable.img_oven_special_stuff
            "pizza pasta" in n -> R.drawable.img_pizza_pasta
            "extreme plus" in n -> R.drawable.img_special_extreme_plus
            "shawarma pizza" in n -> R.drawable.img_shawarma_pizza
            "fajita" in n -> R.drawable.img_special_fajita
            "cheese lover" in n -> R.drawable.img_cheese_lover
            "extra large" in n -> R.drawable.img_extra_large_pizza
            "creamy" in n -> R.drawable.img_creamy_pizza
            "tikka" in n -> R.drawable.img_chicken_tikka
            "extreme" in n -> R.drawable.img_special_extreme
            "emperor" in n && "burger" in n -> R.drawable.img_emperor_burger
            "emperor" in n && "wrap" in n -> R.drawable.img_emperor_wrap
            "emperor" in n -> R.drawable.img_emperor_pizza
            "chicken & chapli" in n -> R.drawable.img_chicken_chapli_burger
            "chapli" in n -> R.drawable.img_chapli_burger
            "oven special grill" in n -> R.drawable.img_oven_special_grill_burger
            "grill burger" in n -> R.drawable.img_grill_burger
            "zinger" in n && "shawarma" in n -> R.drawable.img_zinger_shawarma
            "zinger" in n -> R.drawable.img_zinger_burger
            "tower" in n -> R.drawable.img_tower_burger
            "behari" in n -> R.drawable.img_behari_wrap
            "twister" in n -> R.drawable.img_twister_wrap
            "grill wrap" in n -> R.drawable.img_grill_wrap
            "tortilla" in n -> R.drawable.img_grill_wrap
            "grill sandwich" in n -> R.drawable.img_grill_sandwich
            "pan sandwich" in n -> R.drawable.img_pan_sandwich
            "paratha wrap" in n -> R.drawable.img_paratha_wrap
            "paratha" in n -> R.drawable.img_pizza_paratha
            "chicken cheese" in n && "shawarma" in n -> R.drawable.img_chicken_cheese_shawarma
            "chicken cheese" in n -> R.drawable.img_chicken_cheese_calzone
            "chicken crunchy" in n -> R.drawable.img_chicken_crunchy_calzone
            "malai boti" in n -> R.drawable.img_malai_boti_shawarma
            "grill shawarma" in n -> R.drawable.img_grill_shawarma
            "shawarma platter" in n -> R.drawable.img_shawarma_platter
            "plain shawarma" in n -> R.drawable.img_plain_shawarma
            "shawarma" in n -> R.drawable.img_plain_shawarma
            "pizza platter" in n -> R.drawable.img_pizza_platter
            "loaded fries" in n -> R.drawable.img_loaded_fries
            "regular fries" in n -> R.drawable.img_regular_fries
            "large fries" in n -> R.drawable.img_large_fries
            "fries" in n -> R.drawable.img_loaded_fries
            "hotshots" in n || "nuggets" in n -> R.drawable.img_hotshots
            "special juicy" in n -> R.drawable.img_special_juicy_wings
            "juicy wings" in n -> R.drawable.img_juicy_wings
            "wings" in n -> R.drawable.img_hot_wings
            "fried chest" in n -> R.drawable.img_fried_chest
            "cappuccino" in n -> R.drawable.img_cappuccino
            "latte" in n -> R.drawable.img_latte
            "karak" in n -> R.drawable.img_karak_chai
            "cardamom" in n -> R.drawable.img_cardamom_chai
            "kashmiri" in n -> R.drawable.img_kashmiri_chai
            "green tea" in n -> R.drawable.img_green_tea
            "chai" in n -> R.drawable.img_karak_chai
            "blue sapphire" in n -> R.drawable.img_blue_sapphire
            "blue lagoon" in n -> R.drawable.img_blue_lagoon
            "margarita" in n -> R.drawable.img_mint_margarita
            "ice cream shake" in n -> R.drawable.img_ice_cream_shake
            "cocktail shake" in n -> R.drawable.img_cocktail_shake
            "pina" in n -> R.drawable.img_pina_colada
            "cocktail juice" in n -> R.drawable.img_cocktail_juice
            "cold coffee" in n || "shake" in n -> R.drawable.img_cold_coffee
            "ice cream" in n -> R.drawable.img_ice_cream
            "regular drink" in n -> R.drawable.img_regular_drink
            "500ml" in n -> R.drawable.img_drink_500ml
            "1l drink" in n || "1 litre" in n || "1 liter" in n -> R.drawable.img_drink_1l
            "water large" in n -> R.drawable.img_water_large
            "water small" in n -> R.drawable.img_water_small
            "water" in n -> R.drawable.img_water_small
            "drink" in n -> R.drawable.img_bottled_drink
            "pizza" in n -> R.drawable.img_emperor_pizza
            "burger" in n -> R.drawable.img_chicken_burger
            "wrap" in n -> R.drawable.img_grill_wrap
            else -> R.drawable.img_emperor_pizza
        }
    }
}
