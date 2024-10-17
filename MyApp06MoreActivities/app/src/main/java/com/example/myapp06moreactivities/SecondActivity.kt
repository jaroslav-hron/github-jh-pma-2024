package com.example.myapp06moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp06moreactivities.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nickname = intent.getStringExtra("NICKNAME")
        binding.twInfo.text = "Data z první aktivity. Jméno: $nickname"

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnChooseFruit.setOnClickListener {
            val selectedFruit = when {
                binding.rbJabka.isChecked -> "Jabka"
                binding.rbHrusky.isChecked -> "Hrušky"
                else -> {
                    Toast.makeText(this, "Prosím vyberte ovoce!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("SELECTED_FRUIT", selectedFruit)
            startActivity(intent)
        }
    }
}