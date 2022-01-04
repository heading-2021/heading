package com.kotlin.ori59

import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.provider.ContactsContract
import android.view.View
import android.widget.*

class Tab1AddActivity : AppCompatActivity(), View.OnClickListener {
    private val REQUESTCODE = 123
    lateinit var addName : EditText
    lateinit var addNumber : EditText
    lateinit var addButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.tab1_add)

        addName = findViewById<EditText>(R.id.add_name)
        addNumber = findViewById<EditText>(R.id.add_number)
        addButton = findViewById(R.id.add_button)
        addButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.add_button->{
                val newName = addName.text.toString()
                val newNumber = addNumber.text.toString()
                Log.d("where", "input :"+addName.text)
                Log.d("where","input :"+addNumber.text)
                addName.setText("")
                addNumber.setText("")

                val ops  = arrayListOf<ContentProviderOperation>()
                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,"accountname@gmail.com")
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME,"com.google")
                    .build())
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, newName)
                    .build())
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, newNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                    .build())
                val contentResolver: ContentResolver = this.getContentResolver()
                contentResolver.applyBatch(ContactsContract.AUTHORITY,ops)

                val intent :Intent = Intent(this,MainActivity::class.java)
                this.startActivity(intent)

            }
        }
    }



}