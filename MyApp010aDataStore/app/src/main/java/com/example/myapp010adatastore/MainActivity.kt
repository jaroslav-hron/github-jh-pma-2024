package com.example.myapp010adatastore

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapp010adatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore by preferencesDataStore("user_preferences")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Klíče pro DataStore
    private val NAME_KEY = stringPreferencesKey("name")
    private val AGE_KEY = intPreferencesKey("age")
    private val IS_ADULT_KEY = booleanPreferencesKey("isAdult")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializace bindingu
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Uložení dat do DataStore
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val ageString = binding.etAge.text.toString().trim()

            if (ageString.isBlank()) {
                Toast.makeText(this, "Hele, vyplň věk...", Toast.LENGTH_SHORT).show()
            } else {
                val age = ageString.toInt()
                val isAdult = binding.cbAdult.isChecked
                if ((age < 18 && isAdult) || (age >= 18 && !isAdult)) {
                    Toast.makeText(this, "Kecáš, takže nic ukládat nebudu", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Jasně, ukládám...", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        saveToDataStore(name, age, isAdult)
                    }
                }
            }
        }

        // Načtení dat z DataStore
        binding.btnLoad.setOnClickListener {
            lifecycleScope.launch {
                val name = getFromDataStore(NAME_KEY, "")
                val age = getFromDataStore(AGE_KEY, 0)
                val isAdult = getFromDataStore(IS_ADULT_KEY, false)

                binding.etName.setText(name)
                binding.etAge.setText(age.toString())
                binding.cbAdult.isChecked = isAdult
            }
        }
    }

    // Funkce pro uložení dat do DataStore
    private suspend fun saveToDataStore(name: String, age: Int, isAdult: Boolean) {
        applicationContext.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[AGE_KEY] = age
            preferences[IS_ADULT_KEY] = isAdult
        }
    }

    // Funkce pro načtení dat z DataStore
    private suspend fun <T> getFromDataStore(key: Preferences.Key<T>, defaultValue: T): T {
        val preferences = applicationContext.dataStore.data.first()
        return preferences[key] ?: defaultValue
    }
}