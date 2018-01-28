package com.chandilsachin.notely.fragments.NotesList

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.*

import com.chandilsachin.notely.R
import com.chandilsachin.notely.fragments.NotesDetails.NotesDetailsFragment
import com.chandilsachin.notely.fragments.NotesDetails.NotesDetailsViewModel
import com.chandilsachin.notely.util.initViewModel
import com.chandilsachin.notely.util.lifecycle.arch.LifeCycleFragment
import com.chandilsachin.notely.util.loadFragmentSlideUp
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * Created by sachin on 27/1/18.
 */

class NotesListFragment : LifeCycleFragment() {

    val mViewModel: NotesListViewModel by lazy { initViewModel(NotesListViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.notes_list_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_item_addNewNote -> {
                loadFragmentSlideUp(R.id.fragmentContainer, NotesDetailsFragment.newInstance(-1))
            }
            R.id.menu_item_filterNote -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_notes_list
    }

    override fun init(v: View, savedInstanceState: Bundle?) {
        setUpToolbar(main_toolbar, R.string.app_name)

    }

    override fun initLoadViews() {
        mViewModel.noteLiveData.observe(this, Observer {
            it?.let {
                if (it.isNotEmpty()){

                }
            }

        })
    }

    companion object {

        fun newInstance(): NotesListFragment {
            val fragment = NotesListFragment()
            return fragment
        }
    }

}
