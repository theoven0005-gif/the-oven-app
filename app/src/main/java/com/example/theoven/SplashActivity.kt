package com.example.theoven

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private var navigated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        findViewById<Button>(R.id.btnGetStarted).setOnClickListener {
            navigate()
        }

        // Auto transition after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            navigate()
        }, 3000)
    }

    private fun navigate() {
        if (navigated || isFinishing) return
        navigated = true
        // A Firebase session also counts as logged in (covers fresh installs
        // where the user signed in on another device or the local flag was lost).
        val firebaseSignedIn = try {
            FirebaseHelper.isSignedIn()
        } catch (e: Exception) {
            false
        }
        if (firebaseSignedIn) UserManager.setLoggedIn(this, true)
        val target = if (UserManager.isLoggedIn(this)) HomeActivity::class.java else LoginActivity::class.java
        startActivity(Intent(this, target))
        finish()
    }
}
