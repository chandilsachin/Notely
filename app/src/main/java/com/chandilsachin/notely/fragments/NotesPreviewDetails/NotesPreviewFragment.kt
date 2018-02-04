package com.chandilsachin.notely.fragments.NotesDetails

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.format.DateUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.chandilsachin.notely.MainActivityViewModel
import com.chandilsachin.notely.R
import com.chandilsachin.notely.util.getAppCompactActivity
import com.chandilsachin.notely.util.initViewModel
import com.chandilsachin.notely.util.lifecycle.arch.LifeCycleFragment
import com.chandilsachin.notely.util.loadFragmentSlideUp
import com.chandilsachin.personal_finance.database.entities.Note
import kotlinx.android.synthetic.main.layout_notes_preview.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * Created by sachin on 27/1/18.
 */

class NotesPreviewFragment : LifeCycleFragment() {

    val mViewModel: NotesDetailsViewModel by lazy { initViewModel(NotesDetailsViewModel::class.java) }
    var noteId: Long = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (arguments != null) {
            noteId = arguments?.getLong(PARAM_NOTE_ID) ?: -1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.notes_details_menu_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_edit -> {
                getAppCompactActivity().supportFragmentManager.popBackStack()
                loadFragmentSlideUp(R.id.fragmentContainer, NotesDetailsFragment.newInstance(noteId), NotesDetailsFragment.TAG)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_notes_preview
    }

    override fun init(v: View, savedInstanceState: Bundle?) {
        //setUpToolbar(main_toolbar)

        MainActivityViewModel.loadedFragment = MainActivityViewModel.FRAGMENT_PREVIEW
    }

    override fun initLoadViews() {
        mViewModel.noteLiveData.observe(this, Observer {
            it?.let {
                initView(it)
                noteId = it.noteId
            }
        })
        if (noteId != -1L) {
            mViewModel.getNote(noteId ?: -1)
        }

    }

    companion object {
        const val TAG: String = "NotesPreview"
        const val PARAM_NOTE_ID = "NOTE_ID"

        const val MODE_PREVIEW = 1
        const val MODE_EDIT = 2

        fun newInstance(noteId: Long): NotesPreviewFragment {
            val fragment = NotesPreviewFragment()
            val bundle = Bundle()
            bundle.putLong(PARAM_NOTE_ID, noteId)
            fragment.arguments = bundle
            return fragment
        }
    }

    @SuppressLint("SetTextI18n")
    fun initView(note: Note?) {
        setUpToolbar(main_toolbar1, "")
        setDisplayHomeAsUpEnabled(true)
        note?.let {
            textViewTitle.text = note.title
            textViewTimeStamp.text = "Last updated: ${DateUtils.getRelativeDateTimeString(context, note.timestamp.time,
                    DateUtils.MINUTE_IN_MILLIS, DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME)}"
            webViewContent.loadData(note.noteText, "text/html", "UTF-8")
        }

    }
}
