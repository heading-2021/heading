package com.example.test

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil.setContentView
import com.bumptech.glide.Glide
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView


class Tab3 : Fragment() {
    // TODO: Rename and change types of parameters
    private var mContext : Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mContext = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun gifSpeed(percentage:Float): Float {
        var speed = (percentage/50)*1.2
        if(speed>0)
            return speed.toFloat()
        else
            return 0.1f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab3, container, false)

        var text = view.findViewById<TextView>(R.id.test)
        var btn = view.findViewById<ImageButton>(R.id.imageButton)
//        var gif = GifDrawable(resources, R.drawable.runner_2)
        var gif = GifDrawable(resources, R.raw.runner_1)
        var gif_img = view.findViewById<GifImageView>(R.id.gif)
        gif_img.setImageDrawable(gif)

        val memoryInfo = ActivityManager.MemoryInfo()
        (requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memoryInfo)
        val nativeHeapSize = memoryInfo.totalMem
        val nativeHeapFreeSize = memoryInfo.availMem
        val usedMemInBytes = nativeHeapSize - nativeHeapFreeSize
        val usedMemInPercentage = usedMemInBytes * 100 / nativeHeapSize

        gif.setSpeed(gifSpeed(usedMemInPercentage.toFloat()))
        text.text= usedMemInPercentage.toString()+"%"

        btn.setOnClickListener{
            val memoryInfo_2 = ActivityManager.MemoryInfo()
            (requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(memoryInfo_2)
            val nativeHeapSize_2 = memoryInfo_2.totalMem
            val nativeHeapFreeSize_2 = memoryInfo_2.availMem
            val usedMemInBytes_2 = nativeHeapSize_2 - nativeHeapFreeSize_2
            val usedMemInPercentage_2 = usedMemInBytes_2 * 100 / nativeHeapSize_2

            gif.setSpeed(gifSpeed(usedMemInPercentage_2.toFloat()))
            text.text= usedMemInPercentage_2.toString()+"%"
        }
        return view
    }
}

