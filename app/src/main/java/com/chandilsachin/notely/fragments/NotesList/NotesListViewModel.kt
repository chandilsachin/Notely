package com.chandilsachin.notely.fragments.NotesList

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.chandilsachin.notely.dagger.MyApplication
import com.chandilsachin.personal_finance.database.LocalRepo
import com.chandilsachin.personal_finance.database.entities.Note
import javax.inject.Inject

/**
 * Created by sachin on 28/1/18.
 */
class NotesListViewModel : ViewModel() {

    @Inject
    lateinit var localRepo: LocalRepo;
    val noteLiveData = MutableLiveData<List<Note>>()

    init {
        MyApplication.component.inject(this)
    }

    fun getNotes() {
        localRepo.getAllNotes(0)
                .subscribe({ note ->
                    noteLiveData.value = note
                }, { it.printStackTrace() })
    }

}