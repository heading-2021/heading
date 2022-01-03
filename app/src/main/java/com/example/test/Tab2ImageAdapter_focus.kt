package com.example.test

import android.net.Uri
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView

class Tab2ImageAdapter_focus(private val items: ArrayList<Uri>, val context: Tab2) : RecyclerView.Adapter<Tab2ImageAdapter_focus.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val item = items[position]
        Glide.with(context).load(item)
            .override(500, 500)
            .into(holder.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.tab2_image_item, parent, false)
        return ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view: View = v
        var image = v.findViewById<PhotoView>(R.id.image_focus)

        fun bind(listener: View.OnClickListener, item: String){
            view.setOnClickListener(listener)
        }

//        inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
//            override fun onClick(view: View) {
//                var photoview = view.findViewById<PhotoView>(R.id.photoView)
//                photoview.setImageResource(R.id.image_focus.toInt())
//                Log.d("hihhi", "hello")
//            }
//        }
    }

}

