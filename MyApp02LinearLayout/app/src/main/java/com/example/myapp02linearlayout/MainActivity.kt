package com.example.deviceactivation

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp02linearlayout.R
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var searchText: EditText
    private lateinit var scanBarcodeButton: Button
    private lateinit var addDeviceButton: Button
    private lateinit var addMultipleDevicesButton: Button
    private lateinit var checkDevicesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchText = findViewById(R.id.search_text)
        scanBarcodeButton = findViewById(R.id.scan_barcode_button)
        addDeviceButton = findViewById(R.id.btnAddDevice)
        addMultipleDevicesButton = findViewById(R.id.btnAddDevices)
        checkDevicesButton = findViewById(R.id.btnCheckDevices)


        scanBarcodeButton.setOnClickListener {
            Toast.makeText(this, "Skenování QR a čárového kódu", Toast.LENGTH_SHORT).show()
        }

        addDeviceButton.setOnClickListener {
            val intent = Intent(this, AddDeviceActivity::class.java)
            startActivity(intent)
        }

        addMultipleDevicesButton.setOnClickListener {
            Toast.makeText(this, "Přidání více zařízení", Toast.LENGTH_SHORT).show()
        }

        checkDevicesButton.setOnClickListener {
            Toast.makeText(this, "Kontrola přidaných zařízení a uvedení do provozu", Toast.LENGTH_SHORT).show()
        }
    }
}