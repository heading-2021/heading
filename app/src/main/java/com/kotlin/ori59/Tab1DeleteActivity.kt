package com.kotlin.ori59

import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.provider.ContactsContract
import android.view.View
import android.widget.*

class Tab1DeleteActivity : AppCompatActivity() {
    private val REQUESTCODE = 123
    lateinit var addName : EditText
    lateinit var addNumber : EditText
    lateinit var addButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("where","deleteAcitivty Created")
        val name = intent.getStringExtra("nameToDelete").toString()
        Log.d("where", "nameToDelete : $name")
        val contentResolver : ContentResolver= getContentResolver()
        val where : String = ContactsContract.Data.DISPLAY_NAME + " = ? "
        val params : Array<String> = arrayOf<String>(name)


        val ops  = arrayListOf<ContentProviderOperation>()
        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
            .withSelection(where, params)
            .build());

        try {

            contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);

        } catch (e : RemoteException) {
            val intent :Intent = Intent(this,MainActivity::class.java)
            this.startActivity(intent)
        }
        val intent :Intent = Intent(this,MainActivity::class.java)
        this.startActivity(intent)
    }





}