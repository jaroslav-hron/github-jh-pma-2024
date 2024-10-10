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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnSecond = findViewById<Button>(R.id.btnSecond)
        val etNickname = findViewById<EditText>(R.id.etNickname)

        btnSecond.setOnClickListener{
            val nickname = etNickname.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("NICKNAME", nickname)
            startActivity(intent)
        }

    }
}