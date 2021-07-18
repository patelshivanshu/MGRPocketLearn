package com.example.mgrpocketlearn.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mgrpocketlearn.R
import com.example.mgrpocketlearn.SignInActivity
import com.example.mgrpocketlearn.pdfviewer
import com.example.mgrpocketlearn.ui.logout.LogoutFragment


@Suppress("DEPRECATION")
class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val btn: Button = view.findViewById(R.id.pdf)
        btn.setOnClickListener(this)

        return view
        }
    companion object{
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.pdf -> {

                val intent = Intent(getActivity(), pdfviewer::class.java)
                getActivity()?.startActivity(intent)

            }
            else->{

            }
        }
    }

}
