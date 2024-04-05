package com.example.szlak

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.szlak.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (findViewById<View>(R.id.detail_container) != null) {
            // Na tablecie
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, TrailListFragment())
                    .add(R.id.detail_container, SelectionPromptFragment())
                    .commit()
            }
        } else {
            // Na telefonie
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, TrailListFragment())
                    .commit()
            }
        }
    }
}