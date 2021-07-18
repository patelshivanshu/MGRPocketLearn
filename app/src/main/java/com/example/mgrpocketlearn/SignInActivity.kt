package com.example.mgrpocketlearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()

        forgot_password.setOnClickListener{
            startActivity(Intent(this,forgot_passwordActivity::class.java))
        }

        signin_signup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        login_button.setOnClickListener {
            doLogin()
        }


    }

    private fun doLogin() {
        if (login_Email.text.toString().isEmpty()) {
            login_Email.error = "Please enter email"
            login_Email.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(login_Email.text.toString()).matches()) {
            login_Email.error = "Please enter valid email"
            login_Email.requestFocus()
            return
        }

        if (login_Password.text.toString().isEmpty()) {
            login_Password.error = "Please enter password"
            login_Password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(login_Email.text.toString(), login_Password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {

                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            if(currentUser.isEmailVerified) {
                signin_progress.visibility = View.VISIBLE
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(
                    baseContext, "Please verify your email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                baseContext, "Login failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}

