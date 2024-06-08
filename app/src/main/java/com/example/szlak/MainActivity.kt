package com.example.szlak

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var smsFAB: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        smsFAB = findViewById(R.id.smsFAB)
        smsFAB.show()

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