package com.example.eggtimer.media

import android.content.Context
import android.os.Vibrator

class VibratorHelper(
    context: Context
) {
    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    fun start() {
        vibrator.vibrate(longArrayOf(200, 200, 200, 400), 0)
    }

    fun stop() {
        vibrator.cancel()
    }
}