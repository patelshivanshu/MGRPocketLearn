package com.example.mgrpocketlearn

import android.app.DownloadManager
import android.content.*
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_pdfviewer.*



class pdfviewer : AppCompatActivity() {
    var mydownloadid :Long=0
    lateinit var storage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfviewer)
        storage = Firebase.storage

        button1.setOnClickListener{
            download()


        }
    }


    private fun download() {
        val request=DownloadManager.Request(
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/mgrpocketlearn.appspot.com/o/Syllabus-CC-Exam-Updated.pdf?alt=media&token=93949a30-a3cf-49a4-b248-18d6ef484352"))
            .setTitle("syllubus")
            .setMimeType("application/pdf")
            .setDescription("syllubus downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)


        val dm =getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
       mydownloadid= dm.enqueue(request)
    }
    var br =object :BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            val id =intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
            if(id==mydownloadid){
                Toast.makeText(applicationContext,"syllbaus hasbeen downloaded ",Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun registerReceiver(receiver: BroadcastReceiver?, filter: IntentFilter?): Intent? {
        return super.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }



    private fun view() {
        val intent = Intent(ACTION_VIEW)
        intent.setDataAndType(Uri.parse("https://firebasestorage.googleapis.com/v0/b/mgrpocketlearn.appspot.com/o/Syllabus-CC-Exam-Updated.pdf?alt=media&token=93949a30-a3cf-49a4-b248-18d6ef484352"), "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val newIntent = Intent.createChooser(intent, "Open File")
        try {
            startActivity(newIntent)
        } catch (e: ActivityNotFoundException) {
            // Instruct the user to install a PDF reader here, or something
        }
    }
}








