package com.chandilsachin.personal_finance.database

import com.chandilsachin.notely.dagger.MyApplication
import com.chandilsachin.personal_finance.database.entities.Note
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by sachin on 30/12/17.
 */

class LocalRepo {

    @Inject
    lateinit var dao: NoteDao

    init {
        MyApplication.component.inject(this)
    }

    fun addNote(note: Note): Single<Boolean> {
        return Single.create<Boolean> {
            it.onSuccess(dao.insert(note) != -1L)
        }.subscribeOn(Schedulers.computation())
    }

    fun addNotes(expens: List<Note>): Single<Boolean> {
        return Single.create<Boolean> {
            it.onSuccess(dao.insert(expens).size == expens.size)
        }.subscribeOn(Schedulers.computation())
    }

    fun updateNote(note: Note): Single<Boolean> {
        return Single.create<Boolean> {
            it.onSuccess(dao.update(note) != -1)
        }.subscribeOn(Schedulers.computation())
    }

    fun deleteNote(note: Note): Single<Boolean> {
        return Single.create<Boolean> {
            it.onSuccess(dao.delete(note) > 0)
        }.subscribeOn(Schedulers.computation())
    }

    fun getAllNotes(fetchedCount: Int): Flowable<ArrayList<Note>> {
        return dao.queryAll(/*fetchedCount*/).subscribeOn(Schedulers.computation()).map { ArrayList(it) }
    }

    fun getNote(noteId: Long): Flowable<Note> {
        return dao.query(noteId).subscribeOn(Schedulers.computation())
    }
}
