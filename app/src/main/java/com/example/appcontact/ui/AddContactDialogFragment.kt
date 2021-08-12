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

/** this class is responsible for the pop up dialog fragment to add
    contacts to contact list and firebase*/

class AddContactDialogFragment : DialogFragment() {
    private var _binding : FragmentAddContactDialogBinding? =null
    private val binding get() = _binding!!

    private lateinit var viewModel: ContactViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        the style and theme setting for the add contact dialog fragment
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    _binding = FragmentAddContactDialogBinding.inflate(inflater,container,false)

        /**creating an object of the viewmodel class so that we can asses the add function
         *  on the contact viewmodel so that on clicking of the save button the
         *  information is sent to fire base data base*/
        viewModel = of(this).get(ContactViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.result.observe(viewLifecycleOwner, Observer{
            val message = if (it == null){
                getString(R.string.contacted_has_been_save)
            }else{
                getString(R.string.error, it.message)
            }
            Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
            dismiss()
        })

        /**the save button on the add contact pop dialog, used to save contact also
        send data to firebase data base*/
        binding.buttonAdd.setOnClickListener {
//            getting the information passed in by the user
            val fullName = binding.editTextFullName.text.toString().trim()
            val contactNumber = binding.editTextContact.text.toString().trim()

            /**validating the input from the user to ensure that a user do not submit an empty field*/
            if (fullName.isEmpty()){
                binding.editTextFullName.error = "This field is required"
                return@setOnClickListener
            }
            if (contactNumber.isEmpty()){
                binding.editTextContact.error = "Contact number cannot be empty"
                return@setOnClickListener
            }
            /** creating an object of the contact class and passing in the input from the user
             * and sending it to add function in the viewmodel class to be sent to the data base*/
            val contact = Contact()
            contact.fullName = fullName
            contact.contactNumber = contactNumber

            viewModel.addContact(contact)
        }
    }
}