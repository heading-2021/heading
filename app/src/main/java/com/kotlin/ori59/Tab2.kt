package com.kotlin.ori59

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager


class Tab2 : Fragment() {
    var list = ArrayList<Uri>()
    val adapter = Tab2ImageAdapter(list, this)
    val adapter_2 = Tab2ImageAdapter_focus(list, this)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            var mContext = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        FragmentActivity().supportFragmentManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab2, container, false)

        // 이미지 불러오기 버튼
        var getImage_btn = view.findViewById<Button>(R.id.getImage)
        // 리사이클러뷰
        var recyclerview = view.findViewById<RecyclerView>(R.id.recyclerView)


        getImage_btn.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent, 200)
        }

        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = adapter

        var focus_switch = view.findViewById<Switch>(R.id.focus)

        //  스위치를 클릭했을때
        focus_switch.setOnCheckedChangeListener{CompoundButton, isChecked ->
            //  스위치가 켜지면
            if (isChecked){
                Toast.makeText(context, "focus on", Toast.LENGTH_SHORT).show()
                val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                recyclerview.layoutManager = layoutManager
                recyclerview.adapter = adapter_2
            }
            //  스위치가 꺼지면
            else{
                Toast.makeText(context, "focus off", Toast.LENGTH_SHORT).show()
                val layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerview.layoutManager = layoutManager
                recyclerview.adapter = adapter
            }
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in FragmentActivity().supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }

        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 200) {
            list.clear()

            if (data?.clipData != null) { // 사진 여러개 선택한 경우
                val count = data.clipData!!.itemCount
                if (count > 50) {
                    Toast.makeText(requireContext(), "사진은 50장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                    return
                }
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    list.add(imageUri)
                }

            }
            else { // 단일 선택
                data?.data?.let { uri ->
                    val imageUri : Uri? = data?.data
                    if (imageUri != null) {
                        list.add(imageUri)
                    }
                }
            }
            adapter.notifyDataSetChanged()
            adapter_2.notifyDataSetChanged()

        }

    }

}