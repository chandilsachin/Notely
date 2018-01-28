package com.chandilsachin.notely.util

import android.app.ProgressDialog
import android.content.Context

/**
 * Created by sachin on 1/7/17.
 */
interface SimpleProgressDialog {
    var progressBar: ProgressDialog?
    fun showProgressDialog(context:Context, message: String){
        if(progressBar == null)
        {
            progressBar = ProgressDialog(context)
        }else if(progressBar?.isShowing?:false){
            progressBar?.dismiss()
        }
        progressBar?.setMessage(message)
        progressBar?.show()
    }

    fun dismissProgressDialog(){
        if(progressBar != null)
            progressBar?.dismiss()
    }
}