package com.example.szlak

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logoImageView = findViewById<ImageView>(R.id.logoImageView)

        val fadeInAnimator = ObjectAnimator.ofFloat(logoImageView, "alpha", 0f, 1f)
        fadeInAnimator.duration = 2000 // Czas trwania animacji alfa

        val rotateAnimator = ObjectAnimator.ofFloat(logoImageView, "rotation", 0f, 360f)
        rotateAnimator.duration = 2000 // Czas trwania animacji obrotu

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(fadeInAnimator, rotateAnimator)
        animatorSet.start()


        // Ustaw opóźnienie przed przejściem do MainActivity
        val splashScreenTimeOut = 4000L // Więcej czasu, aby uwzględnić czas obu animacji
        logoImageView.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashScreenTimeOut)
    }
}
