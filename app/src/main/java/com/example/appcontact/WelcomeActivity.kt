package com.example.appcontact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.appcontact.second.ReadPhone
import com.example.appcontact.ui.MainActivity

class WelcomeActivity : AppCompatActivity() {
    lateinit var phoneContact: Button
    lateinit var firbaseContact: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        phoneContact = findViewById(R.id.btn_phone_contact)
        firbaseContact = findViewById(R.id.btn_firbase_contact)

        phoneContact.setOnClickListener {
            val intent = Intent(this,ReadPhone::class.java)
            startActivity(intent)
        }


        firbaseContact.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}