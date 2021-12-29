package com.example.test
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//class Tab1Adapter(private val data : ArrayList<String>) : RecyclerView.Adapter<Tab1Adapter.ItemViewHolder>(){
//    inner class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
//        private val textView1 : TextView = itemView.findViewById(R.id.text1)
//        fun bind(item : String){
//            textView1.text = item
//        }
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder{
//        val context : Context = parent.context
//        val view = LayoutInflater.from(context).inflate(R.layout.tab1_item_recycle,parent,false)
//        return ItemViewHolder(view)
//    }
//    override fun onBindViewHolder(holder:ItemViewHolder,position:Int) {
//        holder.bind(data[position])
//    }
//    override fun getItemCount() : Int { return data.size }
//}

class Tab1Adapter : RecyclerView.Adapter<Tab1Adapter.ViewHolder>() {

    private val name = arrayOf("Kekayaan", "Teknologi",
        "Keluarga", "Bisnis",
        "Keluarga", "Hutang",
        "Teknologi", "Pidana")

    private val phone = arrayOf("1234",
        "5678", "9012", "1111",
        "3333", "5555", "7777",
        "2021")

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var itemName : TextView = itemView.findViewById(R.id.tab1_name)
        var itemPhone : TextView = itemView.findViewById(R.id.tab1_phone)
    }

    override fun onCreateViewHolder(viewGroup :ViewGroup , position : Int): ViewHolder{
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.tab1_item_recycle,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position : Int){
        viewHolder.itemName.text = name[position]
        viewHolder.itemPhone.text = phone[position]
    }
    override fun getItemCount():Int{
        return name.size
    }


}