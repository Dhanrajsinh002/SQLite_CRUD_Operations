package com.example.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.User
import com.example.myapplication.database.SqliteHelper
import kotlinx.android.synthetic.main.activity_edit_user.*


class EditUserActivity : AppCompatActivity() {
    var user:User?=null
    var dbHelper:SqliteHelper?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)
        dbHelper= SqliteHelper(this,null)

        getBundleData()

        buttonUpdate.setOnClickListener{
          if(edName.text.toString()==""){
              Toast.makeText(this,"Please enter name", Toast.LENGTH_SHORT).show()
          }else if(edMobile.text.toString()==""){
              Toast.makeText(this,"Please enter mobile number", Toast.LENGTH_SHORT).show()
          }else {
           updateUserData()
          }
        }
        img_back.setOnClickListener{
            finish()
        }
    }
    private fun getBundleData(){
        if(intent.extras!!.getParcelable<User>("user")!=null)
        {
            user=intent.extras!!.getParcelable<User>("user")
            edName.setText(user!!.name)
            edMobile.setText(user!!.mobileNumber)

        }
    }
    private fun updateUserData(){
    try{
        user!!.name=edName.text.toString()
        user!!.mobileNumber=edMobile.text.toString()

        dbHelper!!.editUserData(user!!)
        Toast.makeText(this,"Update user data successfully", Toast.LENGTH_SHORT).show()
        finish()

    }
    catch (e:Exception){
        Log.e("dbError-->",""+e.message)
    }
    }
}