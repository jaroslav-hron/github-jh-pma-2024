package com.example.moje_fm_app

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DisplayDevicesActivity : AppCompatActivity() {

    private lateinit var deviceListView: ListView
    private lateinit var backButton: Button
    private lateinit var filterButton: Button
    private lateinit var buildingSpinner: Spinner
    private lateinit var standardSpinner: Spinner
    private lateinit var dbHelper: AssetDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_devices)

        deviceListView = findViewById(R.id.deviceListView)
        backButton = findViewById(R.id.backButton)
        filterButton = findViewById(R.id.filterButton)
        buildingSpinner = findViewById(R.id.buildingSpinner)
        standardSpinner = findViewById(R.id.standardSpinner)

        dbHelper = AssetDatabaseHelper(this)

        setupSpinners()
        loadDevices()

        backButton.setOnClickListener {
            finish()
        }

        filterButton.setOnClickListener {
            filterDevices()
        }
    }

    private fun setupSpinners() {
        // Load distinct building names and standards from the database
        val buildings = dbHelper.getDistinctValues(AssetDatabaseHelper.COLUMN_BUILDING)
        val standards = dbHelper.getDistinctValues(AssetDatabaseHelper.COLUMN_STANDARD)

        // Add "Select" option for better clarity
        val buildingAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("Vyberte budovu") + buildings
        )
        val standardAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("Vyberte standard") + standards
        )

        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        standardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        buildingSpinner.adapter = buildingAdapter
        standardSpinner.adapter = standardAdapter
    }


    private fun loadDevices() {
        val devices = dbHelper.getAllDevices()
        displayDevices(devices)
    }

    private fun filterDevices() {
        val selectedBuilding = buildingSpinner.selectedItem.toString()
        val selectedStandard = standardSpinner.selectedItem.toString()

        val filteredDevices = dbHelper.getFilteredDevices(
            if (selectedBuilding == "Vyberte budovu") null else selectedBuilding,
            if (selectedStandard == "Vyberte standard") null else selectedStandard
        )

        displayDevices(filteredDevices)
    }


    private fun displayDevices(devices: List<Map<String, Any>>) {
        if (devices.isEmpty()) {
            Toast.makeText(this, "Žádná zařízení neodpovídají filtru.", Toast.LENGTH_SHORT).show()
            return
        }

        val deviceDescriptions = devices.map { device ->
            """
                Kód: ${device[AssetDatabaseHelper.COLUMN_DEVICE_CODE]}
                Budova: ${device[AssetDatabaseHelper.COLUMN_BUILDING]}
                Podlaží: ${device[AssetDatabaseHelper.COLUMN_FLOOR]}
                Místnost: ${device[AssetDatabaseHelper.COLUMN_ROOM]}
                Popis: ${device[AssetDatabaseHelper.COLUMN_DESCRIPTION]}
                Standard: ${device[AssetDatabaseHelper.COLUMN_STANDARD]}
                Status: ${device[AssetDatabaseHelper.COLUMN_STATUS]}
                Dokončeno: ${if (device[AssetDatabaseHelper.COLUMN_IS_FINALIZED] as Boolean) "Ano" else "Ne"}
            """.trimIndent()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceDescriptions)
        deviceListView.adapter = adapter
    }

}

