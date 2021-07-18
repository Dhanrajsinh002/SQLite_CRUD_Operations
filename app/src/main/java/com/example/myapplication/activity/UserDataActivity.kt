package com.example.myapplication.activity

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.User
import com.example.myapplication.adapter.UserListAdapter
import com.example.myapplication.database.SqliteHelper
import kotlinx.android.synthetic.main.activity_user_data.*


class UserDataActivity : AppCompatActivity() {
    lateinit var userLisAdapter:UserListAdapter
    var dbHelper:SqliteHelper?=null
    var userList=ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)
        dbHelper= SqliteHelper(this,null)


        img_back.setOnClickListener{
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
    }
    private fun setAdapter(){
        userList=getUserListData()
        recyclerViewUserList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        userLisAdapter = UserListAdapter(this, userList){position,user,view->
            when(view.id){
                R.id.textviewName->{
                    navigateToUpdateScreen(user)
                }
                R.id.imgDelete->{
                    deleteUserData(user)
                }
            }
        }
        recyclerViewUserList.adapter=userLisAdapter
    }
    private fun deleteUserData(user:User){
        try{
            progress.visibility= View.VISIBLE
            dbHelper!!.deleteUserData(user)
            userList.remove(user)
            userLisAdapter.notifyDataSetChanged()

            Toast.makeText(this,"Delete user data successfully",Toast.LENGTH_SHORT).show()
            progress.visibility= View.GONE

            }
        catch (e:Exception){
            Log.e("dbError-->",""+e.message)
        }
    }
    private fun navigateToUpdateScreen(user:User){
        var intent= Intent(this,EditUserActivity::class.java)
        intent.putExtra("user",user)
        startActivity(intent)


    }
    private fun getUserListData():ArrayList<User>{
        var userList=ArrayList<User>()
        var cursor:Cursor?=null
        try {
            cursor = dbHelper!!.getAllUserData()
            if(cursor.count>0){
                cursor.moveToFirst()
                var user=User(cursor.getInt(cursor.getColumnIndex(SqliteHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(SqliteHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(SqliteHelper.COLUMN_NUMBER))
                )
                userList.add(user)
                while (cursor.moveToNext()){
                    var user=User(cursor.getInt(cursor.getColumnIndex(SqliteHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(SqliteHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(SqliteHelper.COLUMN_NUMBER))
                    )
                    userList.add(user)

                }
                cursor.close()

            }
        }
        catch (e:Exception){
            Log.e("dberror-->",""+e.message)
        }
        finally {
            if(cursor!=null) {
                cursor.close()
            }
        }
        return userList
    }
}