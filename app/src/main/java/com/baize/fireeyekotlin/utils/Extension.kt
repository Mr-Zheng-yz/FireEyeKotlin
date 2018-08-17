package com.baize.fireeyekotlin.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast

/**
 * Created by 彦泽 on 2018/8/14.
 */
inline fun <reified T:Activity> Activity.newIntent(){
    val intent = Intent(this,T::class.java)
    startActivity(intent)
}

fun Context.showToast(message:String):Toast{
    var toast: Toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
    return toast
}