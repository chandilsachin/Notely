package com.chandilsachin.notely.fragments.NotesList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.chandilsachin.notely.R
import com.chandilsachin.notely.fragments.NotesDetails.NotesDetailsFragment
import com.chandilsachin.notely.util.initViewModel
import com.chandilsachin.notely.util.lifecycle.arch.LifeCycleFragment
import com.chandilsachin.notely.util.loadFragmentSlideUp
import com.chandilsachin.personal_finance.database.entities.Note
import kotlinx.android.synthetic.main.layout_notes_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*

/**
 * Created by sachin on 27/1/18.
 */

class NotesListFragment : LifeCycleFragment() {

    val mViewModel: NotesListViewModel by lazy { initViewModel(NotesListViewModel::class.java) }
    var mAdapter: NotesListAdapter? = null
    var mNavigationListAdapter: NavigationListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.notes_list_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_addNewNote -> {
                loadFragmentSlideUp(R.id.fragmentContainer, NotesDetailsFragment.newInstance(-1))
            }
            R.id.menu_item_filterNote -> {
                toggleNavigationBar()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_notes_list
    }

    override fun init(v: View, savedInstanceState: Bundle?) {
        setUpToolbar(main_toolbar, R.string.app_name)

        mAdapter = NotesListAdapter(context, emptyList())
        rvNotesList.layoutManager = LinearLayoutManager(context)
        rvNotesList.adapter = mAdapter


        mNavigationListAdapter = NavigationListAdapter(context,
                resources.getStringArray(R.array.navigationMenuItems).asList())
        rvNavigation.layoutManager = LinearLayoutManager(context)
        rvNavigation.adapter = mNavigationListAdapter
    }

    override fun initLoadViews() {

        mViewModel.noteLiveData.observe(this, listObserver)
        mViewModel.getNotes()
    }

    var listObserver = Observer<List<Note>> {
        it?.let {
            if (it.isNotEmpty()) {
                mAdapter?.list = it
                mAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun setUpEvents() {
        mAdapter?.onItemClickListener = {
            loadFragmentSlideUp(R.id.fragmentContainer, NotesDetailsFragment.newInstance(it.noteId))
        }
        mAdapter?.onFavoriteClickListener = {
            mViewModel.toggleFavorite(it.noteId)
        }
        mAdapter?.onStarClickListener = {
            mViewModel.toggleStar(it.noteId)
        }
        mAdapter?.isStarred = {
            mViewModel.isStarred(it.noteId)
        }
        mAdapter?.isFavorite = {
            mViewModel.isFavorite(it.noteId)
        }
        ivFilterCancel.setOnClickListener {
            toggleNavigationBar()
            mNavigationListAdapter?.selectedItems?.clear()
            mNavigationListAdapter?.notifyDataSetChanged()
            mViewModel.getNotesFavoriteStarred(mNavigationListAdapter?.selectedItems?.contains(0)?:false,
                    mNavigationListAdapter?.selectedItems?.contains(1)?:false)
        }
        buttonApply.setOnClickListener {
            drawer_layout.closeDrawer(Gravity.END)
            mViewModel.getNotesFavoriteStarred(mNavigationListAdapter?.selectedItems?.contains(0)?:false,
                    mNavigationListAdapter?.selectedItems?.contains(1)?:false)
        }
        mAdapter?.onDeleteItemClickListener = {
            mViewModel.deleteNote(it)
        }
    }

    fun toggleNavigationBar(){
        if (!drawer_layout.isDrawerOpen(Gravity.END))
            drawer_layout.openDrawer(Gravity.END)
        else
            drawer_layout.closeDrawer(Gravity.END)
    }

    companion object {

        fun newInstance(): NotesListFragment {
            val fragment = NotesListFragment()
            return fragment
        }
    }

}
