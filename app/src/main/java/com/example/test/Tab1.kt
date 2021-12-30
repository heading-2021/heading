package com.example.test

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.tab1_fragment.*

class Tab1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<Tab1Adapter.ViewHolder>? = null
    private var mContext : Context? = null
    private var contactsData : MutableList<PhoneBook>? = null


    override fun onAttach(context : Context){
        super.onAttach(context)
//        Log.d("where","tab1Attach")
        if (context is MainActivity) {
            mContext = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        Log.d("where","tab1OnCreateView")
        return inflater.inflate(R.layout.tab1_fragment, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        Log.d("where","tab1OnCreatedView")
        if (mContext!=null) {


            contactsData = getContacts(mContext!!)
            recycler_view.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = Tab1Adapter(contactsData!!, mContext!!)

            }
        }else {throw IllegalArgumentException("cursor is null")}
    }
}

public data class PhoneBook (
    val id : String,
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

            val phoneBook: PhoneBook = PhoneBook(id, name, phone)

            datas.add(phoneBook)
        }
        Log.d("where","beforeclose")
        cursor.close()
    }else {throw IllegalArgumentException("cursor is null")}
    return datas
}




