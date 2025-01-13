package com.example.moje_fm_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.BaseAdapter

class DevicesAdapter(
    private val context: Context,
    private var devices: List<Map<String, Any>>
) : BaseAdapter() {

    private val selectedDevices = mutableSetOf<Int>()

    override fun getCount(): Int = devices.size

    override fun getItem(position: Int): Any = devices[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_device, parent, false)

        val device = devices[position]
        val deviceCode = device[AssetDatabaseHelper.COLUMN_DEVICE_CODE] as String
        val description = device[AssetDatabaseHelper.COLUMN_DESCRIPTION] as String

        val deviceCodeTextView: TextView = view.findViewById(R.id.deviceCodeTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        val checkBox: CheckBox = view.findViewById(R.id.deviceCheckBox)

        deviceCodeTextView.text = deviceCode
        descriptionTextView.text = description

        checkBox.isChecked = selectedDevices.contains(position)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedDevices.add(position)
            } else {
                selectedDevices.remove(position)
            }
        }

        return view
    }

    fun getSelectedDevices(): List<Map<String, Any>> {
        return selectedDevices.map { devices[it] }
    }

    fun updateDevices(newDevices: List<Map<String, Any>>) {
        devices = newDevices
        selectedDevices.clear() // Reset výběru
        notifyDataSetChanged()
    }
}

