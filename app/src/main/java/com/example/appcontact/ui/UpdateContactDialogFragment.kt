package com.example.appcontact.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelProviders.*
import com.example.appcontact.R
import com.example.appcontact.data.Contact
import com.example.appcontact.databinding.FragmentAddContactDialogBinding
import com.example.appcontact.databinding.FragmentUpdateContactDialogBinding

// this class is for the dialog fragment to updating data to firebase
class UpdateContactDialogFragment(private val contact : Contact) : DialogFragment() {
    private var _binding : FragmentUpdateContactDialogBinding? =null
    private val binding get() = _binding!!

    private lateinit var viewModel: ContactViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
    _binding = FragmentUpdateContactDialogBinding.inflate(inflater,container,false)
        viewModel = of(this).get(ContactViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /**   we are taking the save contact info which was passed into this class as an object
        and setting it to as the editTextFullName and editTextContact  */

        binding.editTextFullName.setText(contact.fullName)
        binding.editTextContact.setText(contact.contactNumber)


        /**  on clicking the update button the input retrieved and converted to a
         * string and saved as fullname and contactNumber   */

        binding.buttonUpdate.setOnClickListener {
            val fullName = binding.editTextFullName.text.toString().trim()
            val contactNumber = binding.editTextContact.text.toString().trim()
/** i validate also to ensure input are not empty*/
            if (fullName.isEmpty()){
                binding.editTextFullName.error = "This field is required"
                return@setOnClickListener
            }
            if (contactNumber.isEmpty()){
                binding.editTextContact.error = "Contact number cannot be empty"
                return@setOnClickListener
            }

            contact.fullName = fullName
            contact.contactNumber = contactNumber

            viewModel.updateContact(contact)
            dismiss()
            Toast.makeText(context,"contact have been updated successfully",Toast.LENGTH_LONG).show()

        }
    }
}