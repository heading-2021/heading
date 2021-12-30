package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {



    private fun replaceView(tab: Fragment) {
        //화면 변경
        var selectedFragment: Fragment? = null
        selectedFragment = tab
        selectedFragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, it).commit()
        }
    }

    lateinit var tab1:Tab1
    lateinit var tab2:Tab2
    lateinit var tab3:Tab3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("where","mainOnCreate")


        tab1 = Tab1()
        tab2 = Tab2()
        tab3 = Tab3()

        supportFragmentManager.beginTransaction().add(R.id.frameLayout, tab1).commit()


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //여기에 작성
                when(tab?.position){
                    0 -> {
                        //tab1
//                        val data1 : ArrayList<String> = initData1()
//                        val fm : FragmentManager = getActivity(abd).getFragmentManager()
                        Log.d("where","mainOnTabSelected")
                        replaceView(tab1)
                    }
                    1 -> {
                        replaceView(tab2)
                    }
                    2 -> {
                        replaceView(tab3)
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun initData1() : ArrayList<String>{
        val data1 : ArrayList<String> = ArrayList<String>()
        for (i : Int in 1..20) {
            data1.add(String.format("TEXT %d", i))
        }
        return data1
    }


}