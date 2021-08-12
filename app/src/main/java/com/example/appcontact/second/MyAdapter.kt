package com.example.appcontact.second

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcontact.R

class MyAdapter (private var contact: ArrayList<ContactDTO>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout, parent, false)
        return MyViewHolder(myView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = contact[position]
        holder.name.text = currentItem.names
        holder.numbers.text = currentItem.number
    }

    override fun getItemCount(): Int {
        return contact.size
    }

    /** this is the viewHolder class that holds the view */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textName)
        val numbers: TextView = itemView.findViewById(R.id.phone_no)
    }
}


