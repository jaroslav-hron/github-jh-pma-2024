package com.example.moje_fm_app


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Najdi tlačítko podle ID
        val openAddDeviceActivityButton: Button = findViewById(R.id.addDeviceButton)

        val viewDevicesButton: Button = findViewById(R.id.viewDevicesButton)
        viewDevicesButton.setOnClickListener {
            startActivity(Intent(this, DisplayDevicesActivity::class.java))
        }



        // Nastav akci při kliknutí na tlačítko
        openAddDeviceActivityButton.setOnClickListener {
            // Spusť AddDeviceActivity
            val intent = Intent(this, AddDeviceActivity::class.java)
            startActivity(intent)
        }

    }
}