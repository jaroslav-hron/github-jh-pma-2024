package com.example.vanocni_appka

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import kotlin.random.Random

class SnowfallView(context: Context) : View(context) {
    private val snowflakes = mutableListOf<Snowflake>()
    private var isSnowing = false // Řídí, zda má sněžit

    init {
        for (i in 0..100) {
            snowflakes.add(Snowflake())
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isSnowing) { // Kreslí pouze, když má sněžit
            canvas.let {
                snowflakes.forEach { snowflake ->
                    snowflake.update()
                    it.drawCircle(snowflake.x, snowflake.y, snowflake.size, snowflake.paint)
                }
            }
            invalidate() // Neustále překresluj
        }
    }

    fun toggleSnowing() {
        isSnowing = !isSnowing // Přepínání stavu sněžení
        if (isSnowing) invalidate() // Spusť překreslování, pokud se zapíná sněžení
    }

    private inner class Snowflake {
        var x = Random.nextFloat() * width
        var y = Random.nextFloat() * height
        val size = Random.nextFloat() * 10 + 5
        val paint = Paint().apply { color = Color.WHITE; alpha = 200 }

        fun update() {
            y += size / 2
            if (y > height) {
                y = 0f
                x = Random.nextFloat() * width
            }
        }
    }
}
