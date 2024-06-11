package com.example.szlak

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

class StoperFragment : Fragment(), View.OnClickListener {

    private lateinit var timeView: TextView
    private var stopwatchService: StopwatchService? = null
    private var bound = false
    private val handler = Handler()
    private lateinit var expectedTimeView: TextView

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as StopwatchService.StopwatchBinder
            stopwatchService = binder.getService()
            bound = true
            updateUI()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            bound = false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stoper, container, false)
        timeView = view.findViewById(R.id.time_view)
        expectedTimeView = view.findViewById(R.id.expected_time_view)
        view.findViewById<View>(R.id.start_button).setOnClickListener(this)
        view.findViewById<View>(R.id.stop_button).setOnClickListener(this)
        view.findViewById<View>(R.id.reset_button).setOnClickListener(this)

        val expectedTime = getExpectedTimeFromPreferences()
        expectedTimeView.text = "Przewidywana długość twojej wędrówki: $expectedTime"

        val bottomToolbar = view.findViewById<Toolbar>(R.id.bottom_toolbar)
        bottomToolbar.inflateMenu(R.menu.bottom_toolbar_menu)
        bottomToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_trail_list -> {
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
                else -> false
            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        Intent(activity, StopwatchService::class.java).also { intent ->
            activity?.startService(intent)
            activity?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            activity?.unbindService(serviceConnection)
            bound = false
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.start_button -> stopwatchService?.start()
            R.id.stop_button -> stopwatchService?.stop()
            R.id.reset_button -> stopwatchService?.reset()
        }
        updateUI()
    }

    private fun updateUI() {
        handler.post(object : Runnable {
            override fun run() {
                if (bound) {
                    timeView.text = stopwatchService?.getFormattedTime()
                    handler.postDelayed(this, 1000)
                }
            }
        })
    }

    private fun getExpectedTimeFromPreferences(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("szlak_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("expected_time", "Brak danych")
    }

}
