package com.example.mgrpocketlearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var databaseReference:DatabaseReference?=null
    var database :FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")
        auth = FirebaseAuth.getInstance()



        register_signin.setOnClickListener{
            startActivity(Intent(this@RegisterActivity,SignInActivity::class.java))
        }

        register_button.setOnClickListener {
            signUpUser()
        }

    }

    private fun signUpUser() {
        if (register_Email.text.toString().isEmpty()) {
            register_Email.error = "Please enter email"
            register_Email.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(register_Email.text.toString()).matches()) {
            register_Email.error = "Please enter valid email"
            register_Email.requestFocus()
            return
        }

        if (register_Password.text.toString().isEmpty()) {
            register_Password.error = "Please enter password"
            register_Password.requestFocus()
            return
        }
        if (register_Phone.text.toString().isEmpty()) {
            register_Phone.error = "Please enter Phone Number"
            register_Phone.requestFocus()
            return
        }
        if (register_Name.text.toString().isEmpty()) {
            register_Name.error = "Please enter Full Name"
            register_Name.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(register_Email.text.toString(), register_Password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    register_progressbar.visibility = View.VISIBLE
                    val user = auth.currentUser
                    val currentUserDB =databaseReference?.child((user?.uid!!))
                    currentUserDB?.child("Name")?.setValue(register_Name.text.toString())
                    currentUserDB?.child("email_id")?.setValue(register_Email.text.toString())
                    currentUserDB?.child("Phone_number")?.setValue(register_Phone.text.toString())
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, SignInActivity::class.java))
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }



    }
}

