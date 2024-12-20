package com.example.vanocni_appka

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

class SnowfallView(context: Context) : View(context) {
    private val snowflakes = mutableListOf<Snowflake>()
    var isSnowing = false
    private var speedMultiplier = 1f // Rychlost sněžení

    init {
        for (i in 0..100) {
            snowflakes.add(Snowflake())
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isSnowing) {
            snowflakes.forEach { snowflake ->
                snowflake.update(speedMultiplier)
                canvas.drawCircle(snowflake.x, snowflake.y, snowflake.size, snowflake.paint)
            }
            invalidate()
        }
    }

    fun toggleSnowing() {
        isSnowing = !isSnowing
        if (isSnowing) invalidate()
    }

    fun increaseSpeed() {
        speedMultiplier = (speedMultiplier + 0.5f).coerceAtMost(5f)
    }

    fun decreaseSpeed() {
        speedMultiplier = (speedMultiplier - 0.5f).coerceAtLeast(0.5f)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            snowflakes.find {
                Math.hypot((it.x - event.x).toDouble(), (it.y - event.y).toDouble()) < it.size
            }?.let { snowflake ->
                snowflake.paint.color = Color.TRANSPARENT // Vločka zmizí
            }
        }
        return true
    }

    private inner class Snowflake {
        var x = Random.nextFloat() * width
        var y = Random.nextFloat() * height
        val size = Random.nextFloat() * 10 + 5
        val paint = Paint().apply {
            color = if (Random.nextBoolean()) Color.WHITE else Color.CYAN
            alpha = 200
        }

        fun update(speedMultiplier: Float) {
            y += size / 2 * speedMultiplier
            if (y > height) {
                y = 0f
                x = Random.nextFloat() * width
            }
        }
    }
}
