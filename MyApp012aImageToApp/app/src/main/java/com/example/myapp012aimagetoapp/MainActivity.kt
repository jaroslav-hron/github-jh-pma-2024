package com.example.myapp012aimagetoapp

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp012aimagetoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Request code for storage permission
    private val REQUEST_STORAGE_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializace pro viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Kontrola, zda máme povolení k přístupu do úložiště
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_STORAGE_PERMISSION
            )
        }

        // Registrovaný ActivityResultContract pro vybrání obrázku
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                binding.ivImage.setImageURI(uri)  // Nastaví vybraný obrázek
                showToast("Obrázek byl úspěšně načten.")
            } else {
                showToast("Nezvolil jste žádný obrázek.")
            }
        }

        // Nastavení animace při zobrazení obrázku
        ViewCompat.setTransitionName(binding.ivImage, "imageTransition")

        // Tlačítko pro výběr obrázku
        binding.btnTakeImage.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    // Metoda pro zobrazení Toast zprávy
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Metoda pro zpracování výsledků povolení
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("Povolení bylo uděleno.")
            } else {
                showToast("Povolení k přístupu do úložiště bylo odepřeno.")
            }
        }
    }
}
