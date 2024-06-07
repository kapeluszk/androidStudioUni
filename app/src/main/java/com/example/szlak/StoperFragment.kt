package com.example.szlak

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import java.util.Locale

class StoperFragment : Fragment(), View.OnClickListener {

    private var seconds = 0
    private var running = false
    private var wasRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stoper, container, false)
        runStopwatch(view)
        view.findViewById<View>(R.id.start_button).setOnClickListener(this)
        view.findViewById<View>(R.id.stop_button).setOnClickListener(this)
        view.findViewById<View>(R.id.reset_button).setOnClickListener(this)

        val bottomToolbar = view.findViewById<Toolbar>(R.id.bottom_toolbar)
        bottomToolbar.inflateMenu((R.menu.bottom_toolbar_menu))
        bottomToolbar.setOnMenuItemClickListener{ item ->
            when (item.itemId) {
                R.id.action_trail_list ->{
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, TrailListFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.action_stoper -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, StoperFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }

                else -> {false}
            }
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putInt("seconds", seconds)
        savedInstanceState.putBoolean("running", running)
        savedInstanceState.putBoolean("wasRunning", wasRunning)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.start_button -> onClickStart()
            R.id.stop_button -> onClickStop()
            R.id.reset_button -> onClickReset()
        }
    }

    private fun onClickStart() {
        running = true
    }

    private fun onClickStop() {
        running = false
    }

    private fun onClickReset() {
        running = false
        seconds = 0
    }

    private fun runStopwatch(view: View) {
        val timeView = view.findViewById<TextView>(R.id.time_view)
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60
                val time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs)
                timeView.text = time
                if (running) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        })
    }
}