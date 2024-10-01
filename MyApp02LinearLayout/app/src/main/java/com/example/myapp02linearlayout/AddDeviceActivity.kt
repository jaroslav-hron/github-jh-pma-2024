package com.example.deviceactivation

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp02linearlayout.R

class AddDeviceActivity : AppCompatActivity() {

    private lateinit var deviceNameInput: EditText
    private lateinit var deviceSerialInput: EditText
    private lateinit var deviceTypeInput: EditText
    private lateinit var areaInput: EditText
    private lateinit var submitDeviceButton: Button
    private lateinit var buildingcodeInput: EditText
    private lateinit var FloorCodeInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)

        deviceNameInput = findViewById(R.id.device_id_input)
        deviceSerialInput = findViewById(R.id.serial_number_input)
        deviceTypeInput = findViewById(R.id.standard_input)
        areaInput = findViewById(R.id.area_code_input)
        buildingcodeInput = findViewById(R.id.building_code_input)
        FloorCodeInput = findViewById(R.id.floor_code_input)
        submitDeviceButton = findViewById(R.id.add_button)

        submitDeviceButton.setOnClickListener {
            val deviceName = deviceNameInput.text.toString()
            val deviceSerial = deviceSerialInput.text.toString()
            val deviceType = deviceTypeInput.text.toString()

            if (deviceName.isNotEmpty() && deviceSerial.isNotEmpty() && deviceType.isNotEmpty()) {
                Toast.makeText(this, "Zařízení přidáno: $deviceName", Toast.LENGTH_LONG).show()

                finish()
            } else {
                Toast.makeText(this, "Vyplňte prosím všechna pole", Toast.LENGTH_SHORT).show()
            }
        }
    }
}