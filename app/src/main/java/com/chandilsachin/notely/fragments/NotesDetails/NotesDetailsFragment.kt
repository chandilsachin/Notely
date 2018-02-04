package com.chandilsachin.notely.fragments.NotesDetails

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.chandilsachin.notely.MainActivityViewModel
import com.chandilsachin.notely.R
import com.chandilsachin.notely.util.initViewModel
import com.chandilsachin.notely.util.lifecycle.arch.LifeCycleFragment
import com.chandilsachin.personal_finance.database.entities.Note
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.layout_notes_details.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * Created by sachin on 27/1/18.
 */

class NotesDetailsFragment : LifeCycleFragment() {

    val mViewModel: NotesDetailsViewModel by lazy { initViewModel(NotesDetailsViewModel::class.java) }
    var noteId: Long = -1;

    lateinit var noteObserver: Observer<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (arguments != null) {
            noteId = arguments?.getLong(PARAM_NOTE_ID)!!
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
                if (richEditorNote.title == null)
                    richEditorNote.title = ""
                if (richEditorNote.html == null)
                    richEditorNote.html = ""

                val note = Note(richEditorNote.title.trim(), richEditorNote.html.trim())
                if (noteId != null && noteId != -1L) {
                    note.noteId = noteId as Long
                }
                if (note.title.length < 1 && note.noteText.length < 1) {
                    showToast(R.string.emptyNoteMessage)
                    return true;
                }
                mViewModel.addNote(note).observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            activity?.onBackPressed()
                        }, {})
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_notes_details
    }

    override fun init(v: View, savedInstanceState: Bundle?) {
        setUpToolbar(main_toolbar, "")
        setDisplayHomeAsUpEnabled(true);

        richEditorNote.setEditorFontSize(16)
        richEditorNote.setEditorHeight(200)
        MainActivityViewModel.loadedFragment = MainActivityViewModel.FRAGMENT_DETAILS
    }

    override fun initLoadViews() {
        noteObserver = Observer {
            it?.let {
                richEditorNote.title = it.title
                richEditorNote.html = it.noteText
                noteId = it.noteId
            }
        }

        mViewModel.noteLiveData.observe(this, noteObserver)

        if(mViewModel.note_savedIntance != null){
            mViewModel.note_savedIntance?.let {
                richEditorNote.title = it.title
                richEditorNote.html = it.noteText
                if(it.noteId != -1L)
                    noteId = it.noteId
            }
        }
        else if (noteId != -1L) {
            mViewModel.getNote(noteId ?: -1)
        }
    }

    companion object {
        const val TAG: String = "NotesDetails"
        const val PARAM_NOTE_ID = "NOTE_ID"

        fun newInstance(noteId: Long): NotesDetailsFragment {
            val fragment = NotesDetailsFragment()
            val bundle = Bundle()
            bundle.putLong(PARAM_NOTE_ID, noteId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val note = Note(richEditorNote.title, richEditorNote.html)
        if(noteId != -1L)
            note.noteId = noteId
        mViewModel.note_savedIntance = note
        mViewModel.noteLiveData.value = null
        super.onSaveInstanceState(outState)
    }

}
