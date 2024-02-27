package com.mess.service_co

import Marked
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PersonAdapter(private val personList: ArrayList<Persons>, private val context: Context) :
    RecyclerView.Adapter<PersonAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.each_service, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = personList[position]

        holder.dataName.text = currentItem.dataName
        holder.dataContacts.text = currentItem.dataContact
        holder.dataServices.text = currentItem.dataServices
        holder.dataAdd.text = currentItem.dataAdd
        Glide.with(context).load(currentItem.dataImage).into(holder.dataImage)

        holder.itemView.setOnClickListener { v ->
            val dataImage = currentItem.dataImage
            val dataName = currentItem.dataName
            val dataAdd = currentItem.dataAdd
            val dataContacts = currentItem.dataContact
            val dataServices = currentItem.dataServices

            val intent = Intent(v.context, Marked::class.java)
            intent.putExtra("dataName", dataName)
            intent.putExtra("dataContacts", dataContacts)
            intent.putExtra("dataImage", dataImage)
            intent.putExtra("dataAdd", dataAdd)
            intent.putExtra("dataServices", dataServices)
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dataName: TextView = itemView.findViewById(R.id.fullnames)
        val dataContacts: TextView = itemView.findViewById(R.id.contactnos)
        val dataImage: ImageView = itemView.findViewById(R.id.logoIv)
        val dataServices: TextView = itemView.findViewById(R.id.services)
        val dataAdd: TextView = itemView.findViewById(R.id.address)
    }
}
