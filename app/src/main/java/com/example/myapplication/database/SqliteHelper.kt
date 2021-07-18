package com.example.myapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.User

class SqliteHelper(context:Context,factory:SQLiteDatabase.CursorFactory?):SQLiteOpenHelper(context,
    "Test.db",factory, 2){

    companion object {
        private val DATABASE_VERSION = 2
        private val DATABASE_NAME = "Test.db"
        val TABLE_NAME = "user"
        val COLUMN_ID = "id"
        val COLUMN_NAME = "name"
        val COLUMN_NUMBER = "number"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USER_TABLE= ("CREATE TABLE " +
                TABLE_NAME + "("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NAME + " TEXT,"+ COLUMN_NUMBER + " TEXT"+")")
       db!!.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)

    }
     fun insertUserData(user:User){
       var values=ContentValues()
         values.put(COLUMN_NAME,user.name)
         values.put(COLUMN_NUMBER,user.mobileNumber)
         var db=this.writableDatabase
         db.insert(TABLE_NAME,null,values)
         db.close()
    }

     fun getAllUserData():Cursor{
         val db = this.readableDatabase
         return db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY "+COLUMN_ID+" ASC", null)
    }
     fun editUserData(user:User){
         var values=ContentValues()
         values.put(COLUMN_NAME,user.name)
         values.put(COLUMN_NUMBER,user.mobileNumber)
         var db=this.writableDatabase
         db.update(TABLE_NAME, values, COLUMN_ID + "=" + user.id, null)
         db.close()

     }
     fun deleteUserData(user:User){
         val db = this.writableDatabase
         var id=user.id
         db.execSQL("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = $id")
         db.close()
    }
}