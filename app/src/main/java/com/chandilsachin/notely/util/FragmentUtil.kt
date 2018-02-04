package com.chandilsachin.notely.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.chandilsachin.notely.R

/**
 * Created by sachin on 28/5/17.
 */
fun <T : ViewModel> Fragment.initViewModel(c: Class<T>): T {
    val model = ViewModelProviders.of(this).get(c)
    return model
}

fun Fragment.loadFragment(containerId: Int, fragment: Fragment, tag: String) {
    loadFragment(containerId, fragment, activity as AppCompatActivity, tag)
}

fun Fragment.loadFragmentSlideUp(containerId: Int, fragment: Fragment, tag: String) {
    activity!!.supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up,
                    R.anim.slide_out_down, R.anim.slide_in_down)
            .replace(containerId, fragment, tag)
            .addToBackStack(null).commit()
}

fun loadFragment(containerId: Int, fragment: Fragment, activity: AppCompatActivity, tag: String) {
    loadFragment(containerId, fragment, activity, true, tag)
}

fun loadFragment(containerId: Int, fragment: Fragment, activity: AppCompatActivity, addToBackStack: Boolean, tag: String) {
    val transaction = activity.supportFragmentManager.beginTransaction()
    transaction.replace(containerId, fragment, tag)
    if (addToBackStack)
        transaction.addToBackStack(null)
    transaction.commit()
}


fun Fragment.setSupportActionBar(toolbar: Toolbar) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
}

fun Fragment.setDisplayHomeAsUpEnabled(value: Boolean) {
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(value)
}

fun Fragment.getAppCompactActivity(): AppCompatActivity {
    return activity as AppCompatActivity
}

