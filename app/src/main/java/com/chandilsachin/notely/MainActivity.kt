package com.chandilsachin.notely

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.chandilsachin.notely.fragments.NotesList.NotesListFragment
import com.chandilsachin.notely.util.loadFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(R.id.fragmentContainer, NotesListFragment.newInstance())
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            super.onBackPressed()
        else
            finish()
    }

}
