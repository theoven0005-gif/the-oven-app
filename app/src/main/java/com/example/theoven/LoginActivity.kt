package com.example.theoven

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.theoven.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseNetworkException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Skip login when a Firebase session or a local demo session exists.
        if (isAlreadySignedIn()) {
            goToHome()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetOtp.setOnClickListener { attemptFirebaseLogin() }

        // Offline/demo fallback: the old phone-based quick login.
        binding.btnGoogle.setOnClickListener {
            val phone = binding.etPhone.text.toString().trim()
            demoLogin(phone.ifEmpty { null })
        }
    }

    private fun isAlreadySignedIn(): Boolean {
        return try {
            FirebaseHelper.isSignedIn()
        } catch (e: Exception) {
            false
        } || UserManager.isLoggedIn(this)
    }

    private fun attemptFirebaseLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val phone = binding.etPhone.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Enter a valid email"
            return
        }
        if (password.length < 6) {
            binding.etPassword.error = "Minimum 6 characters"
            return
        }

        setBusy(true)
        try {
            FirebaseHelper.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onFirebaseAuthSuccess(email, phone)
                    } else {
                        val cause = task.exception
                        if (cause is FirebaseNetworkException) {
                            // Offline: fall back to the demo login so the app
                            // stays usable without a network connection.
                            setBusy(false)
                            Toast.makeText(
                                this,
                                "No connection — continuing in offline mode",
                                Toast.LENGTH_SHORT
                            ).show()
                            demoLogin(phone.ifEmpty { null })
                        } else {
                            // Maybe a first-time user: try creating the account.
                            FirebaseHelper.auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { signUp ->
                                    setBusy(false)
                                    if (signUp.isSuccessful) {
                                        onFirebaseAuthSuccess(email, phone)
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Login failed: ${signUp.exception?.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        }
                    }
                }
        } catch (e: Exception) {
            setBusy(false)
            Toast.makeText(this, "Login unavailable — continuing offline", Toast.LENGTH_SHORT).show()
            demoLogin(phone.ifEmpty { null })
        }
    }

    private fun onFirebaseAuthSuccess(email: String, phone: String) {
        val name = UserManager.getName(this).takeIf { it != "Valued Customer" }
            ?: email.substringBefore("@")
        if (phone.isNotEmpty()) UserManager.savePhone(this, phone)
        UserManager.saveUser(this, name, phone, email, UserManager.getAddress(this))
        UserManager.setLoggedIn(this, true)
        // Best-effort Firestore profile + FCM token; never blocks login.
        try {
            FirebaseHelper.ensureUserDocument(name, email, phone)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            FCMTokenManager.refreshAndSave()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        setBusy(false)
        goToHome()
    }

    /** The pre-Firebase demo login, kept as the offline fallback. */
    private fun demoLogin(phone: String?) {
        if (phone != null) {
            if (phone.length in 10..11 && phone.all { it.isDigit() }) {
                UserManager.savePhone(this, phone)
            } else {
                binding.etPhone.error = "Enter valid phone number"
                return
            }
        }
        UserManager.setLoggedIn(this, true)
        goToHome()
    }

    private fun setBusy(busy: Boolean) {
        binding.btnGetOtp.isEnabled = !busy
        binding.progressLogin.visibility = if (busy) View.VISIBLE else View.GONE
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
