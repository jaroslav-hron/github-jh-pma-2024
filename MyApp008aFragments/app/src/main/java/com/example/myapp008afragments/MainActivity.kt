package com.example.myapp008afragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.myapp008afragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//?
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnFragment1.setOnClickListener{
            replaceFragment(Fragment1())
        }
        binding.btnFragment2.setOnClickListener{
            replaceFragment(Fragment2())
        }


    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val  fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.FrCont, fragment)

        fragmentTransaction.commit()
    }
}