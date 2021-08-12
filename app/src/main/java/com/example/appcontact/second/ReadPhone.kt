package com.example.appcontact.second

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcontact.R
import androidx.recyclerview.widget.RecyclerView

const val PHONE_CONST = 10
class ReadPhone : AppCompatActivity() {
    private lateinit var messageButton: TextView
    private lateinit var displayButton: Button
    private lateinit var newArray: ArrayList<ContactDTO>
    lateinit var newRecyclerView: RecyclerView
//    @SuppressLint("Range")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_phone)

            newArray = arrayListOf()
            newRecyclerView = findViewById<RecyclerView>(R.id.recycler_view)
            displayButton = findViewById(R.id.btn_read)
            messageButton = findViewById(R.id.display_text)

            /**this is a button to display the contact on the phone when pressing it*/
            displayButton.setOnClickListener {
                readContact()

            }
        }
        /** this is th function to read contact on the phone*/
//        @SuppressLint("Range")
        private fun readContact(){
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS),
                    PHONE_CONST)
            }else{
                val contact = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null, null)
                if(contact != null){
                    while (contact.moveToNext()){
                        val name = contact.getString(contact.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        val number = contact.getString(contact.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.NUMBER))
                        val imageResource = contact.getString(contact.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                        val obj = ContactDTO(name,number,null)
                        if (imageResource != null){
                            obj.image = MediaStore.Images.Media.getBitmap(
                                contentResolver,
                                Uri.parse(imageResource)

                            )
                        }
                        newArray.add(obj)
                    }
                }
                newRecyclerView.adapter = MyAdapter(newArray)
                newRecyclerView.layoutManager = LinearLayoutManager(this)
                newRecyclerView.setHasFixedSize(true)
                contact?.close()
            }

        }
        /** this is the function to handle the permission using the request callback */
        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == PHONE_CONST){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContact()
                }else{
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
//                    messageButton.visibility = View.VISIBLE
                }
            }


    }
}