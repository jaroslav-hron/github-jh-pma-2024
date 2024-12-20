package com.example.vanocni_appka

object GiftGenerator {
    private val giftNames = listOf(
        "Jednorožčí ponožky",
        "Vánoční hrnek",
        "Turbo sáňky",
        "Zlatá hvězda na stromeček",
        "Magická svíčka",
        "Rukavice se zabudovaným ohřevem",
        "LED diodové sobí rohy",
        "Zmrzlinový stroj na Vánoce",
        "Létající dron soba",
        "Rybářskej kalendář",
        "Párátko",
        "Bonsai",
        "Zvoneček na kolo"



    )

    fun getRandomGift(): String {
        return giftNames.random()
    }
}
