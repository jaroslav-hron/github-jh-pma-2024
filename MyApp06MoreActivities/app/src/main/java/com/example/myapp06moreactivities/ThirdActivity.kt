package com.example.myapp06moreactivities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp06moreactivities.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedFruit = intent.getStringExtra("SELECTED_FRUIT")

        binding.twResult.text = "Vybrali jste: $selectedFruit"

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}