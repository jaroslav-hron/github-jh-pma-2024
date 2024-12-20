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

        val snowView = SnowfallView(this)
        findViewById<FrameLayout>(R.id.snowContainer).addView(snowView)

        findViewById<Button>(R.id.snowButton).setOnClickListener {
            snowView.toggleSnowing()
        }

        findViewById<Button>(R.id.generateGiftButton).setOnClickListener {
            val gift = GiftGenerator.getRandomGift()
            val quote = getRandomChristmasQuote()
            showGiftDialog(gift, quote)
        }

        findViewById<Button>(R.id.increaseSpeedButton).setOnClickListener {
            snowView.increaseSpeed()
        }

        findViewById<Button>(R.id.decreaseSpeedButton).setOnClickListener {
            snowView.decreaseSpeed()
        }
    }

    private fun getRandomChristmasQuote(): String {
        val quotes = listOf(
            "Tohle sis vždycky přál",
            "Užij si to",
            "Přímo od ježíška",
            "Tenhle rok jsi byl hodnej",
            "Hezké svátky"
        )
        return quotes.random()
    }

    private fun showGiftDialog(gift: String, quote: String) {
        val message = "Našli jste pod stromečkem:\n$gift\n\n$quote"
        AlertDialog.Builder(this)
            .setTitle("Tenhle je pro tebe 🎁")
            .setMessage(message)
            .setPositiveButton("Super!") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
