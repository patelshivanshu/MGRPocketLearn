package com.example.mgrpocketlearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class forgot_passwordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        auth = FirebaseAuth.getInstance()

        Reset_button.setOnClickListener {
            forgotPassword()
        }
    }

    private fun forgotPassword() {
        if (forgot_Email.text.toString().isEmpty()) {
            forgot_Email.error = "Please enter email"
            forgot_Email.requestFocus()
            return
        }
        auth.sendPasswordResetEmail(forgot_Email.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext, "Email Sent.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}