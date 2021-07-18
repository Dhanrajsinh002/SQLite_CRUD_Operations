package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.User
import kotlinx.android.synthetic.main.user_list_item.view.*

class UserListAdapter(var context: Context,var userList:ArrayList<User>,var callback: (Int,User,View)->Unit):RecyclerView.Adapter<UserListAdapter.ViewHolder>(){
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.textviewName.text=userList[position].name
        holder.itemView.textViewNumber.text=userList[position].mobileNumber
        holder.itemView.setOnClickListener {
          callback.invoke(position,userList[position],holder.itemView.textviewName)
        }
        holder.itemView.imgDelete.setOnClickListener{
            callback.invoke(position,userList[position],holder.itemView.imgDelete)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}