package com.example.appcontact.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appcontact.data.Contact
import com.example.appcontact.data.NODE_CONTACTS
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

/**  this viewmodel class acts as a controller or the middle way between
  *the app and firebase and also were we host the functions to interact
   *with database such as add, delete or update contact in the database */

class ContactViewModel : ViewModel() {
    private val dbcontacts = FirebaseDatabase.getInstance().getReference(NODE_CONTACTS)

    /** a list of Exceptions to check if the data has been submitted to the database or not
        * if the data has been submitted to the data base we set the list to null
        * if not we print that error message in a toast message*/

    /**this list is used for observing when changes are made to our database such as adding contact
     *  to our database*/
    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result

// to get our contact displayed on my screen
    private val _contact = MutableLiveData<Contact>()
    val contact : LiveData<Contact> get() = _contact

//    the function to add contact to fire base
    fun addContact(contact: Contact){
        contact.id = dbcontacts.push().key  // used in generating a key for the contact object

    /**to save the contact in the firebase we use the below method getting the id and passing in the contact
        * object which firebase will then compared the fields and submit to the database
        * the id is also like an indicator of the path to were the data is store
        * oncompletelistener is also use so as to know if the process is successful
        * */
        dbcontacts.child(contact.id!!).setValue(contact).addOnCompleteListener {
            if (it.isSuccessful){
                _result.value = null
            } else {
                _result.value = it.exception
            }
        }
    }

//    to check when new Contacts are added, in other words to observe our mutable live data*/
    private val childEventListener = object :ChildEventListener{

    /**getting a snapshot of the contact from the data base, i.e creating an object of
    the contact stored on the data base */
    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        val contact = snapshot.getValue(Contact::class.java)
        contact?.id = snapshot.key  // getting the id of the contact
        _contact.value = contact!!  // putting the value into a contact object
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        val contact = snapshot.getValue(Contact::class.java)
        contact?.id = snapshot.key
        _contact.value = contact!!
    }

    override fun onChildRemoved(snapshot: DataSnapshot) {
        val contact = snapshot.getValue(Contact::class.java)
        contact?.id = snapshot.key
        contact?.isDeleted = true
        _contact.value = contact!!
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

    override fun onCancelled(error: DatabaseError) {}

}


    /** the function is used for getting live update from the database
        * this function will be called in our contact fragment to check if a new contact has
     * been added to our database if so then display it*/
    fun getRealTimeUpDate(){
        dbcontacts.addChildEventListener(childEventListener)
    }

//    this function updates the mutable live data
    fun updateContact(contact:Contact){
        dbcontacts.child(contact.id!!).setValue(contact)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value = it.exception
                }
            }
    }

//    function to delete contact
    fun deleteContact(contact: Contact){
        dbcontacts.child(contact.id!!).setValue(null)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _result.value = null
                } else{
                    _result.value = it.exception
                }
            }
    }

    /** it is also a good practice to override on clear to remove the event listener
     * once the fragment is cleared*/
    override fun onCleared() {
        super.onCleared()
        dbcontacts.removeEventListener(childEventListener)
    }
}