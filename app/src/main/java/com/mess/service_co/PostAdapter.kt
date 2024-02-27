package com.mess.service_co

import Dashboard
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(private val postlist: ArrayList<Posts>, private val context: Context) :
    RecyclerView.Adapter<PostAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = postlist[position]

        holder.dataYourname.text = currentItem.dataYourname
        holder.dataDis.text = currentItem.dataDis
        Glide.with(context).load(currentItem.dataImage).into(holder.dataImage)

        holder.itemView.setOnClickListener { v ->
            val dataImage = currentItem.dataImage
            val dataYourname = currentItem.dataYourname
            val dataDis = currentItem.dataDis

            val intent = Intent(v.context, Dashboard::class.java)
            intent.putExtra("dataYourname", dataYourname)
            intent.putExtra("dataDis", dataDis)
            intent.putExtra("dataImage", dataImage)
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return postlist.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dataYourname: TextView = itemView.findViewById(R.id.username)
        val dataDis: TextView = itemView.findViewById(R.id.dis)
        val dataImage: ImageView = itemView.findViewById(R.id.logoIv)
    }
}
