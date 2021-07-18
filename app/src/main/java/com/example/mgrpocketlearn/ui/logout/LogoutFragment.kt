package com.example.mgrpocketlearn.ui.logout

import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.ui.AppBarConfiguration
import com.example.mgrpocketlearn.*
import com.example.mgrpocketlearn.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_logout.*
import kotlinx.android.synthetic.main.fragment_logout.view.*
import kotlinx.android.synthetic.main.nav_header_main.*


class LogoutFragment : Fragment() ,View.OnClickListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var logoutViewModel: LogoutViewModel
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database : FirebaseDatabase?=null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        logoutViewModel =
            ViewModelProviders.of(this).get(LogoutViewModel::class.java)
        auth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")
        val view = inflater.inflate(R.layout.fragment_logout, container, false)
        val btn: Button = view.findViewById(R.id.logout_button)
        btn.setOnClickListener(this)
        return view

    }



    companion object{
        fun newInstance():LogoutFragment{
            return LogoutFragment()
        }
    }

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.logout_button -> {
               auth.signOut()

                val intent =Intent(getActivity(),SignInActivity::class.java)
               getActivity()?.startActivity(intent)
               getActivity()?.finish()
           }
           else->{
               
           }
       }
    }

}


  
























