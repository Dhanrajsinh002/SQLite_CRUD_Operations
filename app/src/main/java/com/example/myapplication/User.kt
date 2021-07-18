package com.example.myapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    var id:Int=0,var name:String,var mobileNumber:String
):Parcelable