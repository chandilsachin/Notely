package com.ace.diettracker.util

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import com.chandilsachin.notely.fragments.NotesList.NotesListAdapter


/**
 * Created by sachin on 3/6/17.
 */
fun View.hideKeyboard(context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun RecyclerView.ViewHolder.fromHtml(html: String): Spanned {
    val result: Spanned
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        result = Html.fromHtml(html)
    }
    return result
}