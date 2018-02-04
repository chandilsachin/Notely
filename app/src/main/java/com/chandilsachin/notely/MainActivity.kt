package com.chandilsachin.notely

import android.animation.Animator
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import com.chandilsachin.notely.fragments.NotesDetails.NotesDetailsFragment
import com.chandilsachin.notely.fragments.NotesList.NotesListFragment
import com.chandilsachin.notely.util.AnimatorAdapter
import com.chandilsachin.notely.util.loadFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        showSplashScreen()
        if (MainActivityViewModel.loadedFragment == MainActivityViewModel.FRAGMENT_LIST) {
            var fragment = supportFragmentManager.findFragmentByTag(NotesListFragment.TAG)
            if (fragment == null)
                fragment = NotesListFragment.newInstance()
            loadFragment(R.id.fragmentContainer, fragment, this, false, NotesListFragment.TAG)
        } else if (MainActivityViewModel.loadedFragment == MainActivityViewModel.FRAGMENT_DETAILS) {
            var fragment = supportFragmentManager.findFragmentByTag(NotesDetailsFragment.TAG)
            if (fragment == null) {
                fragment = NotesDetailsFragment.newInstance(-1)
            }
            loadFragment(R.id.fragmentContainer, fragment, this, false, NotesDetailsFragment.TAG)
        } else
            loadFragment(R.id.fragmentContainer, NotesListFragment.newInstance(), NotesDetailsFragment.TAG)

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            super.onBackPressed()
        else
            finish()
    }

    fun showSplashScreen() {
        if(mViewModel.firstLoad) {
            frameSplashScreen.visibility = View.VISIBLE
            frameSplashScreen.post {
                frameSplashScreen.animate()
                        .y(constraintLayoutMain.bottom.toFloat())
                        .setDuration(resources.getInteger(R.integer.splashHideDuration).toLong())
                        .setStartDelay(resources.getInteger(R.integer.splashStartDuration).toLong())
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .setListener(object : AnimatorAdapter() {
                            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                                frameSplashScreen.visibility = View.GONE
                            }
                        })
                        .start()
            }
            mViewModel.firstLoad = false
        }

//        frameSplashScreen.animate().y(constraintLayoutMain.top.toFloat())
//                .setDuration(200)
//                .setInterpolator(AccelerateDecelerateInterpolator())
//                .start()

    }

}
