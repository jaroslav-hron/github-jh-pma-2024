package com.example.vanocni_appka

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vytvoření instance SnowfallView a přidání do FrameLayout
        val snowView = SnowfallView(this)
        findViewById<FrameLayout>(R.id.snowContainer).addView(snowView)

        // Nastavení klikacího posluchače pro tlačítko sněhu
        findViewById<Button>(R.id.snowButton).setOnClickListener {
            snowView.toggleSnowing()
        }

        // Nastavení klikacího posluchače pro generování dárku
        findViewById<Button>(R.id.generateGiftButton).setOnClickListener {
            val gift = getRandomGift()  // Získání náhodného dárku
            val quote = getRandomChristmasQuote()  // Získání náhodného citátu
            showGiftDialog(gift, quote)  // Zobrazení dialogu s dárkem a citátem
        }

        // Nastavení klikacích posluchačů pro ovládání rychlosti sněhu
        findViewById<Button>(R.id.increaseSpeedButton).setOnClickListener {
            snowView.increaseSpeed()
        }

        findViewById<Button>(R.id.decreaseSpeedButton).setOnClickListener {
            snowView.decreaseSpeed()
        }

        findViewById<Button>(R.id.btnExit).setOnClickListener {
            // Ukončení aplikace
            finishAffinity() // Zavře všechny aktivity aplikace
            System.exit(0) // Ujistí se, že proces je ukončen
        }
    }

    // Metoda pro získání náhodného vánočního citátu
    private fun getRandomChristmasQuote(): String {
        val quotes = listOf(
            "Tohle sis vždycky přál",
            "Užij si to",
            "Přímo od ježíška",
            "Tenhle rok jsi byl hodnej",
            "Hezké svátky"
        )
        return quotes.random()  // Náhodný citát
    }

    // Metoda pro získání náhodného dárku
    private fun getRandomGift(): String {
        val giftNames = listOf(
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
        return giftNames.random()  // Náhodný dárek
    }

    // Metoda pro zobrazení dialogu s dárkem a citátem
    private fun showGiftDialog(gift: String, quote: String) {
        val message = "Našli jste pod stromečkem:\n$gift\n\n$quote"
        AlertDialog.Builder(this)
            .setTitle("Tenhle je pro tebe 🎁")  // Titulek dialogu
            .setMessage(message)  // Zpráva dialogu
            .setPositiveButton("Super!") { dialog, _ -> dialog.dismiss() }  // Tlačítko pro zavření dialogu
            .show()  // Zobrazení dialogu
    }

}
