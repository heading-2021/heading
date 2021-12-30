package com.example.test
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import java.security.AccessController.getContext

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager




class Tab1Adapter(private val datas : MutableList<PhoneBook>,private val context:Context) : RecyclerView.Adapter<Tab1Adapter.ViewHolder>() {




    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var itemName : TextView = itemView.findViewById(R.id.tab1_name)
        var itemPhone : TextView = itemView.findViewById(R.id.tab1_phone)
    }


    override fun onCreateViewHolder(viewGroup :ViewGroup , position : Int): ViewHolder{
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.tab1_item_recycle,viewGroup,false)
//        context = viewGroup.context
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position : Int){
//        val contactsData : MutableList<PhoneBook> = getContacts(context)
//        Log.d("contacts",contactsData.toString())

//        val curdata : PhoneBook= contactsData[position]
        val curdata : PhoneBook= datas[position]

//
        viewHolder.itemName.text = curdata.name
        viewHolder.itemPhone.text = curdata.phone
//          viewHolder.itemName.text = name[position]
//          viewHolder.itemPhone.text = phone[position]
    }
    override fun getItemCount():Int{
        return datas.size
    }


}
