package com.example.beamcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputLength = findViewById<EditText>(R.id.inputLength)
        val inputLoad = findViewById<EditText>(R.id.inputLoad)
        val inputElasticity = findViewById<EditText>(R.id.inputElasticity)
        val inputWidth = findViewById<EditText>(R.id.inputWidth)
        val inputHeight = findViewById<EditText>(R.id.inputHeight)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        calculateButton.setOnClickListener {
            val length = inputLength.text.toString().toDoubleOrNull() ?: 0.0
            val load = inputLoad.text.toString().toDoubleOrNull() ?: 0.0
            val elasticity = inputElasticity.text.toString().toDoubleOrNull() ?: 0.0
            val width = inputWidth.text.toString().toDoubleOrNull() ?: 0.0
            val height = inputHeight.text.toString().toDoubleOrNull() ?: 0.0

            val inertia = (width * height.pow(3.0)) / 12

            val moment = (load * length) / 4

            val deflection = (load * length.pow(3.0)) / (48 * elasticity * inertia)

            val result = """
                Moment setrvačnosti (I) m^4: $inertia
                Ohybový moment (M) Nm: $moment
                Maximální průhyb (y_max) m: $deflection
            """.trimIndent()
            resultText.text = result
        }
    }
}