package com.example.vanocni_appka

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Přidání sněžení na obrazovku
        val snowView = SnowfallView(this)
        findViewById<FrameLayout>(R.id.snowContainer).addView(snowView)

        // Tlačítko pro spuštění/vypnutí sněžení
        findViewById<Button>(R.id.snowButton).setOnClickListener {
            snowView.toggleSnowing() // Přepnutí stavu sněžení
        }

        // Tlačítko pro generování dárku
        findViewById<Button>(R.id.generateGiftButton).setOnClickListener {
            val gift = GiftGenerator.getRandomGift()
            Toast.makeText(this, "Našli jste pod stromečkem $gift", Toast.LENGTH_LONG).show()
        }
    }
}
