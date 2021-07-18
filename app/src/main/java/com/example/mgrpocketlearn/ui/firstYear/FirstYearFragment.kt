package com.example.mgrpocketlearn.ui.firstYear

import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mgrpocketlearn.R
import com.example.mgrpocketlearn.SignInActivity
import kotlinx.android.synthetic.main.fragment_firstyear.*


class FirstYearFragment: Fragment(),View.OnClickListener {

    private lateinit var firstYearViewModel: FirstYearViewModel
    var mydownloadid :Long=0
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        firstYearViewModel =
                ViewModelProviders.of(this).get(FirstYearViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_firstyear, container, false)
        val btn: Button = root .findViewById(R.id.download)
        btn.setOnClickListener(this)
        val btn2: Button = root .findViewById(R.id.view)
        btn2.setOnClickListener(this)
        return root
    }
    companion object{
        fun newInstance():FirstYearFragment{
            return FirstYearFragment()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.download -> {
               download()
            }
            R.id.view -> {
                view()
            }

            else->{

            }
        }
    }
    private fun download() {
        val request= DownloadManager.Request(
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/mgrpocketlearn.appspot.com/o/firstYear%2FB.Tech-CSE_18-19.syllabus.pdf?alt=media&token=83ac588e-af45-4192-b3e8-1ccbe3951331"))
            .setTitle("syllabus")
            .setMimeType("application/pdf")
            .setDescription("syllabus downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)


        val dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        mydownloadid= dm.enqueue(request)
    }
    var br =object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            val id =intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
            if(id==mydownloadid){
                Toast.makeText(activity?.applicationContext,"syllabus has been downloaded ", Toast.LENGTH_SHORT).show()
            }
        }

    }
    fun registerReceiver(receiver: BroadcastReceiver?, filter: IntentFilter?): Intent? {
        return super. getActivity()?.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }
    private fun view() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("https://firebasestorage.googleapis.com/v0/b/mgrpocketlearn.appspot.com/o/firstYear%2FB.Tech-CSE_18-19.syllabus.pdf?alt=media&token=83ac588e-af45-4192-b3e8-1ccbe3951331"), "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val newIntent = Intent.createChooser(intent, "Open File")
        try {
            startActivity(newIntent)
        } catch (e: ActivityNotFoundException) {
            // Instruct the user to install a PDF reader here, or something
        }
    }
}