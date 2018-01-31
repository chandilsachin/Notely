package com.chandilsachin.notely.fragments.NotesDetails

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.*

import com.chandilsachin.notely.R
import com.chandilsachin.notely.util.initViewModel
import com.chandilsachin.notely.util.lifecycle.arch.LifeCycleFragment
import com.chandilsachin.personal_finance.database.entities.Note
import kotlinx.android.synthetic.main.layout_notes_details.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * Created by sachin on 27/1/18.
 */

class NotesDetailsFragment : LifeCycleFragment() {

    val mViewModel: NotesDetailsViewModel by lazy { initViewModel(NotesDetailsViewModel::class.java) }
    var noteId: Long? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (arguments != null) {
            noteId = arguments?.getLong(PARAM_NOTE_ID)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.notes_details_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_undo -> {
                richEditorNote.undo();
            }
            R.id.menu_item_save -> {
                val note = Note(richEditorNote.title.trim(), richEditorNote.html.trim())
                if(noteId != null && noteId != -1L){
                    note.noteId = noteId as Long
                }
                mViewModel.addNote(note)
                activity?.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_notes_details
    }

    override fun init(v: View, savedInstanceState: Bundle?) {
        setUpToolbar(main_toolbar)
        setDisplayHomeAsUpEnabled(true);

        richEditorNote.setEditorFontSize(22)

    }

    override fun initLoadViews() {
        mViewModel.noteLiveData.observe(this, Observer {
            it?.let {
                richEditorNote.title = it.title
                richEditorNote.html = it.noteText
                noteId = it.noteId
            }
        })
        if (noteId != -1L) {
            mViewModel.getNote(noteId ?: -1)
        }
    }

    companion object {

        const val PARAM_NOTE_ID = "NOTE_ID"

        fun newInstance(noteId: Long): NotesDetailsFragment {
            val fragment = NotesDetailsFragment()
            val bundle = Bundle()
            bundle.putLong(PARAM_NOTE_ID, noteId)
            fragment.arguments = bundle
            return fragment
        }
    }

}
