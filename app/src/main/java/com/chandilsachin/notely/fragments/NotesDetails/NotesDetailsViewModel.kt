package com.chandilsachin.notely.fragments.NotesDetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.chandilsachin.notely.dagger.MyApplication
import com.chandilsachin.personal_finance.database.LocalRepo
import com.chandilsachin.personal_finance.database.entities.Note
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by sachin on 28/1/18.
 */
class NotesDetailsViewModel : ViewModel() {

    @Inject
    lateinit var localRepo: LocalRepo;
    val noteLiveData = MutableLiveData<Note>()

    init {
        MyApplication.component.inject(this)
    }

    fun addNote(note: Note) {
        localRepo.addNote(note).subscribe()
    }

    fun getNote(noteId: Long) {
        localRepo.getNote(noteId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ note ->
                    noteLiveData.value = note
                }, { it.printStackTrace() })
    }

}