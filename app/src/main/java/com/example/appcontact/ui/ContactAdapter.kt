package com.example.appcontact.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcontact.data.Contact
import com.example.appcontact.databinding.RecyclerViewContactBinding

class ContactAdapter(
    var listener : ContactClickListener
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

        var contacts = mutableListOf<Contact>()

//    passing our layout file for displaying our card item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewContactBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.binding.textViewName.text = contacts[position].fullName
       holder.binding.textViewContact.text = contacts[position].contactNumber
        holder.binding.contactViewLayout.setOnClickListener {
            listener.onClickContact(position)
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun addContact(contact:Contact){
        if (!contacts.contains(contact)){
            contacts.add(contact)
        } else{
            val index = contacts.indexOf(contact)
            if(contact.isDeleted){
                contacts.removeAt(index)
            }else{
                contacts[index] = contact
            }

        }
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: RecyclerViewContactBinding) : RecyclerView.ViewHolder(binding.root)
    interface ContactClickListener{
        fun onClickContact(position: Int)
    }


}