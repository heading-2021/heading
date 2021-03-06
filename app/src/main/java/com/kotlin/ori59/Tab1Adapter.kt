package com.kotlin.ori59
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class Tab1Adapter(private val datas : MutableList<PhoneBook>,private val context:Context) : RecyclerView.Adapter<Tab1Adapter.ViewHolder>() {


    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.tab1_name)
        var itemPhone: TextView = itemView.findViewById(R.id.tab1_phone)
        var itemPhoto: ImageView = itemView.findViewById(R.id.tab1_photo)
        private val callButton: TextView = itemView.findViewById(R.id.call)
        private val coverItem : ViewGroup= itemView.findViewById(R.id.tab1_item_view)
        private val deleteButton : TextView = itemView.findViewById(R.id.delete)
        init {
            coverItem.setOnClickListener(View.OnClickListener {
                Log.d("where","coverItem clicked!")
                true
            })
            callButton.setOnClickListener(View.OnClickListener {
                Log.d("where","button clicked!")

                var intent = Intent(Intent.ACTION_CALL)
                intent.data= Uri.parse("tel:"+itemPhone.text)

                context.startActivity(intent)

            })
            deleteButton.setOnClickListener(View.OnClickListener {
                Log.d("where","delete button clicked!")
                val intent : Intent = Intent(context, Tab1DeleteActivity::class.java)
                intent.putExtra("nameToDelete",itemName.text)
                context.startActivity(intent)
            })
        }

    }
    override fun onCreateViewHolder(viewGroup :ViewGroup , position : Int): ViewHolder{
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.tab1_item_recycle,viewGroup,false)
//        context = viewGroup.context



        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position : Int){
        val curdata : PhoneBook= datas[position]

//
        viewHolder.itemName.text = curdata.name
        viewHolder.itemPhone.text = curdata.phone
        viewHolder.itemPhoto.setImageResource(R.drawable.person_icon)
//          viewHolder.itemName.text = name[position]
//          viewHolder.itemPhone.text = phone[position]
    }
    override fun getItemCount():Int{
        return datas.size
    }


}
