package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.User
import com.example.myapplication.database.SqliteHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var dbHelper: SqliteHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = SqliteHelper(this, null)

        buttonSaveData.setOnClickListener {
            if (edName.text.toString() == "") {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show()
            } else if (edMobile.text.toString() == "") {
                Toast.makeText(this, "Please enter mobile number", Toast.LENGTH_SHORT).show()
            } else {
                saveUserData()
            }
        }
        buttonViewAllData.setOnClickListener {
            navigateNextScreen()
        }
    }

    private fun saveUserData() {
        var user = User(0, edName.text.toString(), edMobile.text.toString())
        try {
            dbHelper!!.insertUserData(user)
            Toast.makeText(this, "Save Data Successfully", Toast.LENGTH_SHORT).show()
            clearFiledValue()
            navigateNextScreen()
        } catch (e: Exception) {
            Log.e("dbError-->", "" + e.message)
        }
    }

    private fun navigateNextScreen() {
        var intent = Intent(this, UserDataActivity::class.java)
        startActivity(intent)
    }

    private fun clearFiledValue() {
        edName.setText("")
        edMobile.setText("")
    }

}