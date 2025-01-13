package com.example.moje_fm_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AddDeviceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adding)

        // Tlačítko "Přidat více zařízení"
        val addMoreDeviceButton: Button = findViewById(R.id.addMultipleDevicesButton)
        addMoreDeviceButton.setOnClickListener {
            val intent = Intent(this, AddMoreDeviceActivity::class.java)
            startActivity(intent)
        }

        val checkAndImportDevicesButton: Button = findViewById(R.id.checkAndImportDevicesButton)
        checkAndImportDevicesButton.setOnClickListener {
            val intent = Intent(this, CheckAndFinalizeDevicesActivity::class.java)
            startActivity(intent)
        }

        // Tlačítko "Zpět"
        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish() // Ukončí aktuální aktivitu a vrátí se na předchozí obrazovku
        }
    }
}
