package com.chandilsachin.notely.database

import com.chandilsachin.notely.dagger.MyApplication
import com.chandilsachin.notely.database.entities.StarredNotes
import com.chandilsachin.personal_finance.database.NoteDao
import com.chandilsachin.personal_finance.database.entities.FavoriteNote
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

    fun getAllNotes(): Flowable<List<Note>> {
        return dao.queryAll()
    }

    fun getAllNotesFavoriteStarred(): Flowable<List<Note>> {
        return dao.queryAllFavoriteStarred()
    }

    fun getAllNotesFavorite(): Flowable<List<Note>> {
        return dao.queryAllFavorite()
    }

    fun getAllNotesStarred(): Flowable<List<Note>> {
        return dao.queryAllStarred()
    }

    fun getNote(noteId: Long): Flowable<Note> {
        return dao.query(noteId).subscribeOn(Schedulers.computation())
    }

    fun doFavorite(favoriteNote: FavoriteNote): Single<Boolean> {
        return Single.create<Boolean> {
            dao.insert(favoriteNote)
            it.onSuccess(true)
        }.subscribeOn(Schedulers.io())
    }

    fun isFavorite(noteId: Long): Single<Boolean> {
        return dao.isFavorite(noteId).subscribeOn(Schedulers.io())
    }

    fun removeFavorite(favoriteNote: FavoriteNote): Single<Boolean> {
        return Single.create<Boolean> {
            dao.delete(favoriteNote)
            it.onSuccess(false)
        }.subscribeOn(Schedulers.io())
    }

    fun doStar(starredNote: StarredNotes): Single<Boolean> {
        return Single.create<Boolean> {
            dao.insert(starredNote)
            it.onSuccess(true)
        }.subscribeOn(Schedulers.io())
    }

    fun isStar(noteId: Long): Single<Boolean> {
        return dao.isStarred(noteId).subscribeOn(Schedulers.io())
    }

    fun removeStar(starredNote: StarredNotes): Single<Boolean> {
        return Single.create<Boolean> {
            dao.delete(starredNote)
            it.onSuccess(false)
        }.subscribeOn(Schedulers.io())
    }
}
