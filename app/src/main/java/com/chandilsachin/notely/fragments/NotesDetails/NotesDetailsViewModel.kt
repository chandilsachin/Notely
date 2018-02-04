package com.chandilsachin.notely.fragments.NotesDetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.chandilsachin.notely.dagger.MyApplication
import com.chandilsachin.notely.database.LocalRepo
import com.chandilsachin.personal_finance.database.entities.Note
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by sachin on 28/1/18.
 */
class NotesDetailsViewModel : ViewModel() {

    @Inject
    lateinit var localRepo: LocalRepo;
    val noteLiveData = MutableLiveData<Note>()
    var note_savedIntance: Note? = null

    init {
        MyApplication.component.inject(this)
    }

    fun addNote(note: Note): Single<Boolean> {
        return localRepo.addNote(note)
    }

    fun getNote(noteId: Long) {
        localRepo.getNote(noteId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ note ->
                    noteLiveData.value = note
                }, { it.printStackTrace() })
    }

}