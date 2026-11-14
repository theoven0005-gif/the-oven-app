package com.example.theoven

object MenuRepository {
    val fullMenu = listOf(
        // Pizzas - The Oven Royalties
        MenuItem(1, "Arabic Bite", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1300, "Large" to 1800), imageRes = R.drawable.ic_pizza_real),
        MenuItem(2, "Emperor Pizza", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1550, "Large" to 2050), imageRes = R.drawable.ic_pizza_real),
        MenuItem(3, "The Oven Special Stuff", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1550, "Large" to 2150), imageRes = R.drawable.ic_pizza_real),
        MenuItem(4, "Pizza Pasta", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1500, "Large" to 2050), imageRes = R.drawable.ic_pizza_real),
        MenuItem(5, "Crown Crust", FoodCategory.ROYALTY_PIZZA, mapOf("Medium" to 1200, "Large" to 1550), "Signature pizza with a crust made of cheese-filled crowns", imageRes = R.drawable.crown_crust),
        MenuItem(6, "Special Extreme Plus", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 850, "Medium" to 1550, "Large" to 2050), imageRes = R.drawable.ic_pizza_real),
        MenuItem(7, "Shawarma Pizza", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 700, "Medium" to 1050, "Large" to 1400), imageRes = R.drawable.ic_pizza_real),
        MenuItem(8, "Creamy Pizza", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 700, "Medium" to 1100, "Large" to 1350), "Rich creamy sauce with tender chicken malai boti", imageRes = R.drawable.pizza_special),
        MenuItem(9, "Special Fajita", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 650, "Medium" to 950, "Large" to 1150), imageRes = R.drawable.ic_pizza_real),
        MenuItem(10, "Chicken Tikka", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 650, "Medium" to 950, "Large" to 1150), imageRes = R.drawable.ic_pizza_real),
        MenuItem(11, "Cheese Lover", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 450, "Medium" to 800, "Large" to 950), "Without chicken", imageRes = R.drawable.ic_pizza_real),
        MenuItem(12, "Special Extreme", FoodCategory.ROYALTY_PIZZA, mapOf("Small" to 750, "Medium" to 1200, "Large" to 1550), imageRes = R.drawable.ic_pizza_real),
        MenuItem(50, "Extra Large Pizza", FoodCategory.ROYALTY_PIZZA, mapOf("Regular Flavour" to 2100, "Special Flavour" to 2700), "16 inch extra large pizza - Tikka, Fajita, Creamy or Extreme", imageRes = R.drawable.ic_pizza_real),

        // Burgers & More
        MenuItem(13, "Zinger Burger", FoodCategory.BURGERS, mapOf("Standard" to 420), "Crispy fried chicken fillet with spicy mayo and lettuce", imageRes = R.drawable.zinger),
        MenuItem(14, "Chicken Burger", FoodCategory.BURGERS, mapOf("Standard" to 350), imageRes = R.drawable.ic_burger_real),
        MenuItem(15, "Tower Burger", FoodCategory.BURGERS, mapOf("Standard" to 700), imageRes = R.drawable.burger_special),
        MenuItem(16, "Chicken & Chapli Burger", FoodCategory.BURGERS, mapOf("Standard" to 450), imageRes = R.drawable.ic_burger_real),
        MenuItem(17, "Chapli Burger", FoodCategory.BURGERS, mapOf("Standard" to 350), imageRes = R.drawable.ic_burger_real),
        MenuItem(18, "Grill Burger", FoodCategory.BURGERS, mapOf("Standard" to 600), imageRes = R.drawable.ic_burger_real),
        MenuItem(19, "Oven Special Grill Burger", FoodCategory.BURGERS, mapOf("Standard" to 700), imageRes = R.drawable.ic_burger_real),
        MenuItem(20, "Emperor Burger", FoodCategory.BURGERS, mapOf("Standard" to 800), "Double patty giant burger with extra cheese", imageRes = R.drawable.burger_special),

        // Wraps & Sandwiches
        MenuItem(21, "Tortilla Grill Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 550), imageRes = R.drawable.ic_burger_real),
        MenuItem(22, "Special Behari Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 650), "Spicy chicken behari boti rolled in a fresh paratha", imageRes = R.drawable.ic_burger_real),
        MenuItem(23, "Special Emperor Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 650), imageRes = R.drawable.ic_burger_real),
        MenuItem(24, "Grill Sandwich", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 700), imageRes = R.drawable.ic_burger_real),
        MenuItem(25, "Paratha Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 400), imageRes = R.drawable.ic_burger_real),
        MenuItem(26, "Twister Wrap", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 400), imageRes = R.drawable.ic_burger_real),
        MenuItem(27, "Pan Sandwich", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 700), imageRes = R.drawable.ic_burger_real),
        MenuItem(28, "Chicken Cheese", FoodCategory.WRAPS_SANDWICHES, mapOf("Small" to 550, "Large" to 800), "Cheesy chicken calzone", imageRes = R.drawable.ic_pizza_real),
        MenuItem(29, "Chicken Crunchy", FoodCategory.WRAPS_SANDWICHES, mapOf("Small" to 550, "Large" to 800), "Crunchy chicken calzone", imageRes = R.drawable.ic_pizza_real),
        MenuItem(52, "Pizza Paratha", FoodCategory.WRAPS_SANDWICHES, mapOf("Standard" to 450), "The Oven specialty fusion: pizza toppings inside a crispy paratha", imageRes = R.drawable.pizza_fries),

        // Shawarma
        MenuItem(30, "Plain Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 250), imageRes = R.drawable.ic_burger_real),
        MenuItem(31, "Chicken Cheese Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 320), imageRes = R.drawable.ic_burger_real),
        MenuItem(32, "Zinger Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 380), imageRes = R.drawable.ic_burger_real),
        MenuItem(33, "Malai Boti Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 300), imageRes = R.drawable.ic_burger_real),
        MenuItem(34, "Grill Shawarma", FoodCategory.SHAWARMA, mapOf("Standard" to 300), imageRes = R.drawable.ic_burger_real),
        MenuItem(35, "Shawarma Platter", FoodCategory.SHAWARMA, mapOf("Standard" to 550), imageRes = R.drawable.ic_burger_real),

        // Fries & Sides
        MenuItem(36, "Loaded Fries", FoodCategory.FRIES_SIDES, mapOf("Small" to 500, "Medium" to 750, "Large" to 1050), imageRes = R.drawable.pizza_fries),
        MenuItem(37, "Regular Fries", FoodCategory.FRIES_SIDES, mapOf("Regular" to 250), imageRes = R.drawable.pizza_fries),
        MenuItem(38, "Large Fries", FoodCategory.FRIES_SIDES, mapOf("Large" to 400), imageRes = R.drawable.pizza_fries),
        MenuItem(39, "Hotshots Nuggets", FoodCategory.FRIES_SIDES, mapOf("Standard" to 400), "Crispy chicken hotshots nuggets", imageRes = R.drawable.pizza_fries),
        MenuItem(51, "Pizza Platter", FoodCategory.FRIES_SIDES, mapOf("Standard" to 1200), "Large fries + 5 HotShots + half large pizza", imageRes = R.drawable.pizza_fries),

        // Wings
        MenuItem(40, "Hot Wings", FoodCategory.WINGS, mapOf("5 pc" to 380, "10 pc" to 720), imageRes = R.drawable.bbq_placeholder),
        MenuItem(41, "Juicy Wings", FoodCategory.WINGS, mapOf("5 pc" to 400, "10 pc" to 800), imageRes = R.drawable.bbq_placeholder),
        MenuItem(42, "Special Juicy Wings", FoodCategory.WINGS, mapOf("5 pc" to 500, "10 pc" to 850), imageRes = R.drawable.bbq_placeholder),
        MenuItem(43, "Fried Chest Piece", FoodCategory.WINGS, mapOf("Standard" to 500), imageRes = R.drawable.bbq_placeholder),

        // Coffee & Chai
        MenuItem(44, "Cappuccino", FoodCategory.COFFEE, mapOf("Standard" to 180), imageRes = R.drawable.banner_1),
        MenuItem(45, "Latte", FoodCategory.COFFEE, mapOf("Standard" to 180), imageRes = R.drawable.banner_1),
        MenuItem(46, "Karak Chai", FoodCategory.COFFEE, mapOf("Standard" to 100), imageRes = R.drawable.banner_1),
        MenuItem(53, "Cardamom Chai", FoodCategory.COFFEE, mapOf("Standard" to 160), imageRes = R.drawable.banner_1),
        MenuItem(54, "Kashmiri Chai", FoodCategory.COFFEE, mapOf("Standard" to 130), imageRes = R.drawable.banner_1),
        MenuItem(55, "Green Tea", FoodCategory.COFFEE, mapOf("Standard" to 100), imageRes = R.drawable.banner_1),

        // Drinks Bar
        MenuItem(47, "Blue Lagoon", FoodCategory.DRINKS_BAR, mapOf("Standard" to 150), imageRes = R.drawable.banner_2),
        MenuItem(48, "Cold Coffee", FoodCategory.DRINKS_BAR, mapOf("Standard" to 300), imageRes = R.drawable.banner_2),
        MenuItem(56, "Blue Sapphire", FoodCategory.DRINKS_BAR, mapOf("Standard" to 200), imageRes = R.drawable.banner_2),
        MenuItem(57, "Mint Margrita", FoodCategory.DRINKS_BAR, mapOf("Standard" to 200), imageRes = R.drawable.banner_2),
        MenuItem(58, "Ice Cream Shake", FoodCategory.DRINKS_BAR, mapOf("Standard" to 300), imageRes = R.drawable.banner_2),
        MenuItem(59, "Cocktail Shake", FoodCategory.DRINKS_BAR, mapOf("Standard" to 300), imageRes = R.drawable.banner_2),
        MenuItem(60, "Pina Colada", FoodCategory.DRINKS_BAR, mapOf("Standard" to 250), imageRes = R.drawable.banner_2),
        MenuItem(61, "Cocktail Juice", FoodCategory.DRINKS_BAR, mapOf("Standard" to 200), imageRes = R.drawable.banner_2),

        // Bottled Drinks
        MenuItem(49, "1.5L Drink", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 230), imageRes = R.drawable.banner_2),
        MenuItem(62, "Regular Drink", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 100), imageRes = R.drawable.banner_2),
        MenuItem(63, "500ml Drink", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 120), imageRes = R.drawable.banner_2),
        MenuItem(64, "1L Drink", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 180), imageRes = R.drawable.banner_2),
        MenuItem(65, "Water Large", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 120), imageRes = R.drawable.banner_2),
        MenuItem(66, "Water Small", FoodCategory.BOTTLED_DRINKS, mapOf("Standard" to 70), imageRes = R.drawable.banner_2),

        // Ice Cream
        MenuItem(67, "Special Ice Cream", FoodCategory.ICE_CREAM, mapOf("Single Scoop" to 120), "Single scoop of special ice cream", imageRes = R.drawable.banner_1)
    )

    val deals = listOf(
        Deal(1, "Deal 1", "2 Zinger Burgers + 500ml Drink", 890, R.drawable.zinger),
        Deal(2, "Deal 2", "4 Zinger Burgers + 1 LTR Drink", 1760, R.drawable.zinger),
        Deal(3, "Deal 3", "1 Zinger Burger + Regular Fries + 500ml Drink", 720, R.drawable.zinger),
        Deal(4, "Deal 4", "8 Zinger Burgers + 1.5 LTR Drink", 3470, R.drawable.zinger),
        Deal(5, "Deal 5", "2 Small Pizzas + 500ml Drink", 1350, R.drawable.ic_pizza_real),
        Deal(6, "Deal 6", "2 Medium Pizzas + 1 LTR Drink", 1980, R.drawable.ic_pizza_real),
        Deal(7, "Deal 7", "2 Large Pizzas + 1.5 LTR Drink", 2410, R.drawable.ic_pizza_real),
        Deal(8, "Deal 8", "1 Medium Pizza + 1 Small Pizza + 1 LTR Drink", 1680, R.drawable.ic_pizza_real),
        Deal(9, "Deal 9", "1 Large Pizza + 1 Medium Pizza + 1.5 LTR Drink", 2210, R.drawable.ic_pizza_real),
        Deal(10, "Deal 10 (NEW)", "1 Small Creamy Pizza + 1 Small Extreme Pizza + 500ml Drink", 1500, R.drawable.pizza_special),
        Deal(11, "Deal 11 (NEW)", "1 Medium Creamy Pizza + 1 Medium Extreme Pizza + 1 LTR Drink", 2380, R.drawable.pizza_special),
        Deal(12, "Deal 12 (NEW)", "1 Large Creamy Pizza + 1 Large Extreme Pizza + 1.5 LTR Drink", 3010, R.drawable.pizza_special),
        Deal(13, "Deal 13", "2 Pizza Paratha + 500ml Drink", 1450, R.drawable.pizza_fries),
        Deal(14, "Deal 14", "1 Small Regular Pizza + 1 Zinger Burger + 500ml Drink", 1120, R.drawable.ic_pizza_real),
        Deal(15, "Deal 15", "1 Large Pizza + 10 Hot Wings + 1.5 LTR Drink", 1980, R.drawable.ic_pizza_real),
        Deal(16, "Deal 16", "1 Medium Pizza + 5 Hot Wings + 1 LTR Drink", 1410, R.drawable.ic_pizza_real),
        Deal(17, "Deal 17", "4 Plain Chicken Shawarmas + 1 LTR Drink", 1080, R.drawable.ic_burger_real),
        Deal(18, "Deal 18", "1 Small Pizza + 1 Half Pasta + 5 Hot Wings + 1 LTR Drink", 1660, R.drawable.ic_pizza_real),
        Deal(19, "Deal 19", "1 Medium Pizza + 2 Zinger Burgers + 1 Half Pasta + 5 Hot Wings + 1.5 LTR Drink", 2830, R.drawable.ic_pizza_real),
        Deal(20, "Deal 20", "1 Large Pizza (Tikka/Fajita) + 2 Zinger Burgers + 1 Full Pasta + 10 Hot Wings + 1.5 LTR Drink", 3620, R.drawable.ic_pizza_real),
        Deal(21, "Party Deal 21", "2 Large Pizzas (Tikka/Fajita) + 4 Zinger Burgers + 1 Full Pasta + 1 Half Pasta + 10 Hot Wings + 2x1L Drinks", 6230, R.drawable.ic_pizza_real),
        Deal(22, "Party Deal 22", "1 Crown Crust Pizza (Large) + 1 Afghani Pizza (Large) + 2 Zinger Burgers + 10 Hot Wings + 2x1L Drinks", 5290, R.drawable.crown_crust)
    )
}
