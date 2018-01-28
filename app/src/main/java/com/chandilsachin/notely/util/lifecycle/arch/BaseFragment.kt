package com.chandilsachin.notely.util.lifecycle.arch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem

open class BaseFragment<E> : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                val fm = activity!!.supportFragmentManager
                if (fm.backStackEntryCount > 0) fm.popBackStack()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}