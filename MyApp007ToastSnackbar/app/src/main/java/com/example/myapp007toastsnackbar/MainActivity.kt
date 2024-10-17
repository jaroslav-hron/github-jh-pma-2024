package com.example.myapp007toastsnackbar

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputBinding
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp007toastsnackbar.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonToast.setOnClickListener {
            val toast = Toast.makeText(this,"Nazdar mám hlad", Toast.LENGTH_LONG)
            toast.show()
        }
        binding.buttonSnackbar.setOnClickListener {
            Snackbar.make(binding.root,"Nazdar mám hlad", Snackbar.LENGTH_LONG)
                .setDuration(7000)
                .setBackgroundTint(Color.parseColor("#FF3578"))
                .setTextColor(Color.BLACK)
                .setActionTextColor(Color.WHITE)

                .setAction("Zavřít"){
                    Toast.makeText(this, "Zavírám Snackbar", Toast.LENGTH_LONG).show()
                }
                .show()


        }
    }
}