package com.example.myapp06moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp06moreactivities.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "První aktivita"


        binding.btnSecond.setOnClickListener {
            val nickname = binding.etNickname.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("NICKNAME", nickname)
            startActivity(intent)
        }
    }
}