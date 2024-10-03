package com.example.myapp004objednavka

import android.os.Binder
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp004objednavka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Objednávka kachny"

        binding.BtnObjednat.setOnClickListener {
            val KachnaRbid = binding.RGvyberZvere.checkedRadioButtonId

            val kachna = findViewById<RadioButton>(KachnaRbid)

            val zvuk = binding.cbZvuk.isChecked
            val bio = binding.cbBio.isChecked
            val barvapremium = binding.cbBarvaPremium.isChecked

            val ObjednavkaText = "Souhrn objednávky: " +
                    "${kachna.text}" +
                    (if(zvuk) "; má lepší zvuk" else "") +
                    (if(bio) "; obsahuje biosložky" else "") +
                    (if(barvapremium) "; má kvalitnější nátěr" else "")


            binding.Objednavka.text = ObjednavkaText
        }

        binding.tvSamec.setOnClickListener{
            binding.ImgZver.setImageResource(R.drawable.samec)
        }
        binding.tvSamice.setOnClickListener{
            binding.ImgZver.setImageResource(R.drawable.samice)
        }
        binding.tvMlade.setOnClickListener{
            binding.ImgZver.setImageResource(R.drawable.mlade)
        }

}
}