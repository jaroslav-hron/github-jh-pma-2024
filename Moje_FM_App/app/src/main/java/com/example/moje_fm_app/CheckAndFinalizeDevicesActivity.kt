package com.example.moje_fm_app

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheckAndFinalizeDevicesActivity : AppCompatActivity() {

    private lateinit var devicesListView: ListView
    private lateinit var finalizeButton: Button
    private lateinit var cancelButton: Button
    private lateinit var dbHelper: AssetDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_finalize_devices)

        devicesListView = findViewById(R.id.devicesListView)
        finalizeButton = findViewById(R.id.finalizeButton)
        cancelButton = findViewById(R.id.cancelButton)
        dbHelper = AssetDatabaseHelper(this)

        // Načti všechna nefinalizovaná zařízení z databáze
        val devices = dbHelper.getUnfinalizedDevices()
        val devicesAdapter = DevicesAdapter(this, devices)
        devicesListView.adapter = devicesAdapter

        // Tlačítko Finalizovat
        finalizeButton.setOnClickListener {
            val selectedDevices = devicesAdapter.getSelectedDevices()
            if (selectedDevices.isEmpty()) {
                Toast.makeText(this, "Žádná zařízení nebyla vybrána.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var successCount = 0
            var failureCount = 0

            for (device in selectedDevices) {
                val deviceCode = device[AssetDatabaseHelper.COLUMN_DEVICE_CODE] as String
                val isFinalized = dbHelper.finalizeDevice(deviceCode)
                if (isFinalized) {
                    successCount++
                } else {
                    failureCount++
                }
            }

            Toast.makeText(
                this,
                "Zařízení finalizována: $successCount, Neúspěšná finalizace: $failureCount",
                Toast.LENGTH_LONG
            ).show()

            if (failureCount == 0) {
                // Znovu načíst seznam zařízení, aby se již finalizovaná zařízení nezobrazovala
                val updatedDevices = dbHelper.getUnfinalizedDevices()
                devicesAdapter.updateDevices(updatedDevices)
            }
        }

        // Tlačítko Zrušit
        cancelButton.setOnClickListener {
            finish() // Ukončí aktivitu a vrátí se zpět
        }
    }


}
