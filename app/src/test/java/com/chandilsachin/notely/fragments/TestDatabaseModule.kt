package com.chandilsachin.notely.fragments

import android.content.Context
import com.chandilsachin.notely.dagger.DatabaseModule
import com.chandilsachin.notely.database.entities.StarredNotes
import com.chandilsachin.personal_finance.database.NoteDao
import com.chandilsachin.personal_finance.database.entities.FavoriteNote
import com.chandilsachin.personal_finance.database.entities.Note
import io.reactivex.Flowable
import io.reactivex.Single

class TestDatabaseModule : DatabaseModule() {

    override fun getDatabaseObject(context: Context?): NoteDao {
        return NoteDaoImpl()
    }
}

class NoteDaoImpl: NoteDao{
    companion object {
        private var db = HashMap<Int, Note>()
        private var favoriteDb = HashMap<Int, FavoriteNote>()
        private var starredDb = HashMap<Int, StarredNotes>()
        private  var idCount = 0

        fun flush(){
            db.clear()
            favoriteDb.clear()
            starredDb.clear()
            idCount = 0
        }
    }



    override fun insert(note: Note): Long {
        note.noteId = idCount++.toLong()
        db.put(note.noteId.toInt(), note)
        return idCount.toLong()
    }

    override fun insert(notes: List<Note>): List<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(note: Note): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(note: Note): Int {
        db.remove(note.noteId.toInt())
        return 1;
    }

    override fun queryAll(): Flowable<List<Note>> {
        return Flowable.just(db.map { it.value }.toList())
    }

    override fun queryAllFavoriteStarred(): Flowable<List<Note>> {
        return Flowable.just(db.filter { favoriteDb.containsKey(it.key) || starredDb.containsKey(it.key) }
                .map { it.value }.toList())
    }

    override fun queryAllFavorite(): Flowable<List<Note>> {
        return Flowable.just(db.filter { favoriteDb.containsKey(it.key) }
                .map { it.value }.toList())
    }

    override fun queryAllStarred(): Flowable<List<Note>> {
        return Flowable.just(db.filter { starredDb.containsKey(it.key) }
                .map { it.value }.toList())
    }

    override fun query(expenseId: Long): Flowable<Note> {
        return Flowable.just(db.get(expenseId.toInt()))
    }

    override fun insert(favorite: FavoriteNote) {
        favoriteDb.put(favorite.noteId.toInt(), favorite)
    }

    override fun delete(favorite: FavoriteNote) {
        favoriteDb.remove(favorite.noteId.toInt())
    }

    override fun isFavorite(noteId: Long): Single<Boolean> {
        return Single.just<Boolean>(favoriteDb.containsKey(noteId.toInt()))
    }

    override fun insert(favorite: StarredNotes) {
        starredDb.put(favorite.noteId.toInt(), favorite)
    }

    override fun delete(favorite: StarredNotes) {
        starredDb.remove(favorite.noteId.toInt())
    }

    override fun isStarred(noteId: Long): Single<Boolean> {
        return Single.just<Boolean>(starredDb.containsKey(noteId.toInt()))
    }

}