package com.example.moje_fm_app


import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddMoreDeviceActivity : AppCompatActivity() {

    private lateinit var devicePrefix: EditText
    private lateinit var deviceCode: EditText
    private lateinit var totalDevices: EditText
    private lateinit var deviceBuilding: EditText
    private lateinit var deviceFloor: EditText
    private lateinit var deviceRoom: EditText
    private lateinit var deviceDescription: EditText
    private lateinit var deviceStandard: EditText
    private lateinit var operationalStatusSpinner: Spinner
    private lateinit var addDevicesButton: Button
    private lateinit var loadOrderButton: Button // Nové tlačítko
    private lateinit var cancelButton: Button

    private lateinit var dbHelper: AssetDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_more_device)

        // Najdi UI prvky
        devicePrefix = findViewById(R.id.devicePrefix)
        deviceCode = findViewById(R.id.deviceCode)
        totalDevices = findViewById(R.id.totalDevices)
        deviceBuilding = findViewById(R.id.deviceBuilding)
        deviceFloor = findViewById(R.id.deviceFloor)
        deviceRoom = findViewById(R.id.deviceRoom)
        deviceDescription = findViewById(R.id.deviceDescription)
        deviceStandard = findViewById(R.id.deviceStandard)
        operationalStatusSpinner = findViewById(R.id.operationalStatusSpinner)
        addDevicesButton = findViewById(R.id.addDevicesButton)
        loadOrderButton = findViewById(R.id.fetchLastNumberButton) // Nové tlačítko
        cancelButton = findViewById(R.id.cancelButton)

        dbHelper = AssetDatabaseHelper(this)

        // Nastavení Spinneru
        val statusOptions = arrayOf("Chybí", "Ukradeno", "V provozu", "Prodáno", "Mimo provoz")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        operationalStatusSpinner.adapter = spinnerAdapter

        // Logika pro tlačítko "Načíst pořadí"
        loadOrderButton.setOnClickListener {
            val prefix = devicePrefix.text.toString()
            if (prefix.isNotEmpty()) {
                val nextNumber = dbHelper.getLastNumberWithPrefix(prefix) + 1
                deviceCode.setText("$prefix$nextNumber") // Nastavení nového kódu zařízení
                Toast.makeText(this, "Načteno pořadí: $nextNumber pro prefix: $prefix", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Zadejte prefix!", Toast.LENGTH_SHORT).show()
            }
        }

        // Přidání více zařízení
        addDevicesButton.setOnClickListener {
            val prefix = devicePrefix.text.toString()
            val total = totalDevices.text.toString().toIntOrNull() ?: 0
            val building = deviceBuilding.text.toString()
            val floor = deviceFloor.text.toString()
            val room = deviceRoom.text.toString()
            val description = deviceDescription.text.toString()
            val standard = deviceStandard.text.toString()
            val status = operationalStatusSpinner.selectedItem.toString()

            // Validace vstupů
            if (prefix.isEmpty() || total <= 0 || standard.isEmpty()) {
                Toast.makeText(this, "Vyplňte všechna povinná pole!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val startingNumber = dbHelper.getLastNumberWithPrefix(prefix) + 1

            // Přidání zařízení do databáze
            for (i in 0 until total) {
                val deviceCode = "$prefix${startingNumber + i}" // Generování unikátního kódu zařízení
                val values = ContentValues().apply {
                    put("device_code", deviceCode)
                    put("building", building)
                    put("floor", floor)
                    put("room", room)
                    put("description", description)
                    put("standard", standard)
                    put("status", status)
                }

                if (!dbHelper.insertAsset(values)) {
                    Toast.makeText(this, "Chyba: Kód zařízení $deviceCode již existuje.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            Toast.makeText(this, "Zařízení přidána: $total, Prefix: $prefix", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Storno
        cancelButton.setOnClickListener {
            finish()
        }
    }
}
