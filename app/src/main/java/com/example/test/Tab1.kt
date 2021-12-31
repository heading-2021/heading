package com.example.test

import android.Manifest
import android.app.AlertDialog
import android.content.ClipData
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.FragmentTab1Binding
import kotlinx.android.synthetic.main.fragment_tab1.*

class Tab1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<Tab1Adapter.ViewHolder>? = null
    private var mContext : Context? = null
    private var contactsData : MutableList<PhoneBook>? = null
    private var fragmentTab1Binding: FragmentTab1Binding? = null
    private val binding get() = fragmentTab1Binding!!

    override fun onRequestPermissionsResult(requestCode: Int, permissions:Array<String>, grantResults:IntArray){
        when (requestCode) {
            1000 -> {
                if ((grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED)) {
                    //권한 획득 성공
                }else{
                    //권한 획득 실패
                }
            }
        }
    }

    override fun onAttach(context : Context){
        super.onAttach(context)
//        Log.d("where","tab1Attach")
        if (context is MainActivity) {
            mContext = context
            if (mContext == null){
                throw java.lang.IllegalArgumentException("mContext is null")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        Log.d("where","tab1OnCreateView")
        val binding = FragmentTab1Binding.inflate(inflater,container,false)
        fragmentTab1Binding = binding
        return fragmentTab1Binding!!.root
//        return inflater.inflate(R.layout.fragment_tab1, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        //permission checking
        if (!checkPermission(arrayOf(Manifest.permission.READ_CONTACTS,Manifest.permission.CALL_PHONE))) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(requireActivity(),arrayOf(Manifest.permission.READ_CONTACTS,Manifest.permission.CALL_PHONE), 1000)
//                showExplanation(mContext!!,"Permission needed","give permission",Manifest.permission.READ_CONTACTS,1000)
            } else {
                ActivityCompat.requestPermissions(requireActivity(),arrayOf(Manifest.permission.READ_CONTACTS,Manifest.permission.CALL_PHONE), 1000)
            }
        }

        if (checkPermission(arrayOf(Manifest.permission.READ_CONTACTS,Manifest.permission.CALL_PHONE))) {
            Log.d("where", "tab1 OnCreatedView")
            //get contacts data and attach them to the adapter
            if (mContext != null) {
                contactsData = getContacts(mContext!!)
                val myAdapter : Tab1Adapter = Tab1Adapter(contactsData!!, mContext!!)
                recycler_view.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = myAdapter
                }
                val swipeHelperCallback = Tab1SwipeHelperCallback(myAdapter).apply {
                    // 스와이프한 뒤 고정시킬 위치 지정
                    setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)    // 1080 / 4 = 270
                }
                ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.recyclerView)
                // 구분선 추가
//                binding.recyclerView.addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))

                // 다른 곳 터치 시 기존 선택했던 뷰 닫기
                binding.recyclerView.setOnTouchListener { _, _ ->
                    swipeHelperCallback.removePreviousClamp(binding.recyclerView)
                    false
                }

            } else {
                throw IllegalArgumentException("cursor is null")
            }
        } else {
            throw IllegalArgumentException("permission error")
        }
    }
    private fun checkPermission(permissions: Array<String>): Boolean { return permissions.all { ContextCompat.checkSelfPermission(mContext!!, it) == PackageManager.PERMISSION_GRANTED } }

}


public data class PhoneBook (
    val id : String,
    val userPhoto : Int,
    val name : String,
    val phone : String
)

public fun getContacts(context: Context) : MutableList<PhoneBook>{
    // 데이터베이스 혹은 content resolver 를 통해 가져온 데이터를 적재할 저장소를 먼저 정의
    val datas : MutableList<PhoneBook> = mutableListOf<PhoneBook>()

    // 1. Resolver 가져오기(데이터베이스 열어주기)
    // 전화번호부에 이미 만들어져 있는 ContentProvider 를 통해 데이터를 가져올 수 있음
    // 다른 앱에 데이터를 제공할 수 있도록 하고 싶으면 ContentProvider 를 설정
    // 핸드폰 기본 앱 들 중 데이터가 존재하는 앱들은 Content Provider 를 갖는다
    // ContentResolver 는 ContentProvider 를 가져오는 통신 수단
    val resolver : ContentResolver = context.getContentResolver()

    // 2. 전화번호가 저장되어 있는 테이블 주소값(Uri)을 가져오기
    val phoneUri : Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    // 3. 테이블에 정의된 칼럼 가져오기
    // ContactsContract.CommonDataKinds.Phone 이 경로에 상수로 칼럼이 정의
    val projection : Array<String> = arrayOf(ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER);
    // 4. 커서로 리턴된다. 반복문을 돌면서 cursor 에 담긴 데이터를 하나씩 추출
    val cursor : Cursor? = resolver.query(phoneUri, projection, null, null, null)

    if (cursor != null) {
        while (cursor.moveToNext()) {
            // 4.1 이름으로 인덱스를 찾아준다
            val idIndex: Int = cursor.getColumnIndex(projection[0])
            val nameIndex: Int = cursor.getColumnIndex(projection[1])
            val phoneIndex: Int = cursor.getColumnIndex(projection[2])
            // 4.2 해당 index 를 사용해서 실제 값을 가져온다.
            val id: String = cursor.getString(idIndex)
            val name: String = cursor.getString(nameIndex)
            val phone: String = cursor.getString(phoneIndex)

            val phoneBook: PhoneBook = PhoneBook(id, 1, name, phone)

            datas.add(phoneBook)
        }
        cursor.close()
    }else {throw IllegalArgumentException("cursor is null")}
    return datas
}