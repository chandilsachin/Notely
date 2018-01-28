package com.chandilsachin.notely.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by sachin on 24/5/17.
 */
fun <T:ViewModel> AppCompatActivity.initViewModel(c:Class<T>):T{
    val model = ViewModelProviders.of(this).get(c)
    return model
}

fun AppCompatActivity.loadFragment(containerId:Int, fragment:Fragment){
    loadFragment(containerId, fragment, this)
}

fun AppCompatActivity.showToast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showToast(message:Int){
    Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
}

