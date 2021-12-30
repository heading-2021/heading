package com.example.test

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Tab2 : Fragment() {
    var list = ArrayList<Uri>()
    val adapter = MultiImageAdapter(list, requireContext())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_tab2, container, false)

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

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = adapter

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 200) {
            list.clear()

            if (data?.clipData != null) { // 사진 여러개 선택한 경우
                val count = data.clipData!!.itemCount
                if (count > 20) {
//                    Toast.makeText(applicationContext, "사진은 20장까지 선택 가능합니다.", Toast.LENGTH_LONG)
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

        }

    }

}