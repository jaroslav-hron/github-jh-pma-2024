package com.example.vanocni_appka

object GiftGenerator {
    private val giftNames = listOf(
        "jednorožčí ponožky",
        "vánoční hrnek",
        "turbo sáňky",
        "zlatá hvězda na stromeček",
        "magická svíčka",
        "rukavice se zabudovaným ohřevem",
        "LED diodové sobí rohy",
        "zmrzlinový stroj na Vánoce",
        "létající dron soba",
        "rybářskej kalendář",
        "párátko",
        "bonsai",
        "zvoneček na kolo"



    )

    fun getRandomGift(): String {
        return giftNames.random()
    }
}
