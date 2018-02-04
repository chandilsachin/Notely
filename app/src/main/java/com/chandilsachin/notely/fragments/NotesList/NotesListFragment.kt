package com.chandilsachin.notely.fragments.NotesList

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.ace.diettracker.util.setTintDrawable
import com.chandilsachin.notely.MainActivityViewModel
import com.chandilsachin.notely.R
import com.chandilsachin.notely.fragments.NotesDetails.NotesDetailsFragment
import com.chandilsachin.notely.fragments.NotesDetails.NotesPreviewFragment
import com.chandilsachin.notely.util.initViewModel
import com.chandilsachin.notely.util.lifecycle.arch.LifeCycleFragment
import com.chandilsachin.notely.util.loadFragmentSlideUp
import com.chandilsachin.personal_finance.database.entities.Note
import kotlinx.android.synthetic.main.layout_notes_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * Created by sachin on 27/1/18.
 */

class NotesListFragment : LifeCycleFragment() {

    val mViewModel: NotesListViewModel by lazy { initViewModel(NotesListViewModel::class.java) }
    var mAdapter: NotesListAdapter? = null
    var mNavigationListAdapter: NavigationListAdapter? = null
    var mFilterApplied = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.notes_list_menu, menu)
        menu?.let {
            val filterDrawable = menu.findItem(R.id.menu_item_filterNote)
            if(mFilterApplied) {
                setTintDrawable(filterDrawable.icon, ContextCompat.getColor(context!!, R.color.navigationFilterSelectedIconColor))
            }else{
                setTintDrawable(filterDrawable.icon, ContextCompat.getColor(context!!, R.color.toolBarIconTintColor))
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_addNewNote -> {
                loadFragmentSlideUp(R.id.fragmentContainer, NotesDetailsFragment.newInstance(-1), NotesDetailsFragment.TAG)
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
        MainActivityViewModel.loadedFragment = MainActivityViewModel.FRAGMENT_LIST
    }

    override fun initLoadViews() {

        mViewModel.noteLiveData.observe(this, listObserver)
        compositeDisposable.add(mViewModel.getNotes())
    }

    var listObserver = Observer<List<Note>> {
        it?.let {
            if (it.isNotEmpty()) {
                mAdapter?.list = it
                mAdapter?.notifyDataSetChanged()
            }else{
                mAdapter?.list = emptyList()
                mAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun setUpEvents() {
        mAdapter?.onItemClickListener = {
            loadFragmentSlideUp(R.id.fragmentContainer, NotesPreviewFragment.newInstance(it.noteId), NotesPreviewFragment.TAG)
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
            compositeDisposable.clear()
            compositeDisposable.add(mViewModel.getNotesFavoriteStarred(false, false))
            mFilterApplied = false
            activity?.invalidateOptionsMenu()
        }
        buttonApply.setOnClickListener {
            drawer_layout.closeDrawer(Gravity.END)
            compositeDisposable.add(mViewModel.getNotesFavoriteStarred(mNavigationListAdapter?.selectedItems?.contains(0)?:false,
                    mNavigationListAdapter?.selectedItems?.contains(1)?:false))
            mFilterApplied = true
            activity?.invalidateOptionsMenu()
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

        const val TAG: String = "NotesList"

        fun newInstance(): NotesListFragment {
            val fragment = NotesListFragment()
            return fragment
        }

        /*fun getInstance(supportFragmentManager: FragmentManager): NotesListFragment {
            var fragment = supportFragmentManager.findFragmentByTag(TAG)
            if(fragment == null)
                fragment = newInstance()
            return fragment as NotesListFragment
        }*/
    }

}
