package com.example.szlak

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import java.util.Locale

class StopwatchService : Service() {

    private val binder = StopwatchBinder()
    private var seconds = 0
    private var running = false
    private val handler = Handler()

    private val runnable = object : Runnable {
        override fun run() {
            if (running) {
                seconds++
            }
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate() {
        super.onCreate()
        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun start() {
        running = true
    }

    fun stop() {
        running = false
    }

    fun reset() {
        running = false
        seconds = 0
    }

    fun isRunning(): Boolean {
        return running
    }

    fun getFormattedTime(): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs)
    }

    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
    }
}
