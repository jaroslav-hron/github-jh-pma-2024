package com.example.myapp05jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePerson()
        }
    }
}
/*
Tato funkce definuje samotnou Composable, což je funkce
v Jetpack Compose, která vykresluje UI.
V tomto případě bude tato funkce obsahovat
veškerou logiku a UI pro tuto jednoduchou aplikaci.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposePerson() {

}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposePerson()
}