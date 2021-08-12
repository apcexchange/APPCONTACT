package com.example.appcontact.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.appcontact.R

class ContactDetails : AppCompatActivity() {
    lateinit var contactdetailsPhone: ImageView
    lateinit var contactName: TextView
    lateinit var phoneNumber: TextView
    var num:String? = null
    var nam: String? = null
    var phoneNum: String? = null

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        contactdetailsPhone = findViewById(R.id.contact_details_phone)
        contactName = findViewById(R.id.tv_name)
        phoneNumber = findViewById(R.id.phone_number)
        val backArrow = findViewById<ImageView>(R.id.back_arrow)
        val shareButton = findViewById<ImageView>(R.id.contact_share)


        backArrow.setOnClickListener {
            onBackPressed()
        }

        contactName.text=intent.getStringExtra("name")
        phoneNumber.text=intent.getStringExtra("phoneNumber")
        num=intent.getStringExtra("phoneNumber")
        nam = intent.getStringExtra("contact_name")
        num = intent.getStringExtra("number     ")

        contactdetailsPhone.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,Array(1){
                    android.Manifest.permission.CALL_PHONE},113)
            } else callPhone()
        }

        shareButton.setOnClickListener {
            share()
        }

    }
    private fun callPhone() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$num")
        startActivity(callIntent)
    }

    private fun share(){
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.ACTION_SEND, "contact \n $nam \n $num")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent,null)
        startActivity(shareIntent)
    }

}