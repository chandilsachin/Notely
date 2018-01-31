package com.chandilsachin.personal_finance.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.chandilsachin.notely.database.entities.StarredNotes
import com.chandilsachin.personal_finance.database.entities.Note
import com.chandilsachin.personal_finance.database.entities.FavoriteNote
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by sachin on 22/5/17.
 */
@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    fun insert(note: Note): Long

    @Insert(onConflict = REPLACE)
    fun insert(notes: List<Note>): List<Long>

    @Update
    fun update(note: Note): Int

    @Delete
    fun delete(note: Note): Int

    @Query("SELECT * FROM ${Constants.TABLE_NOTES} ORDER BY ${Constants.TIMESTAMP} DESC")
    fun queryAll():Flowable<List<Note>>

    @Query("select * from ${Constants.TABLE_NOTES} nl where nl.${Constants.NOTE_ID} in (select fn.${Constants.NOTE_ID} from FavoriteNote fn) or nl.${Constants.NOTE_ID} in (select fn.${Constants.NOTE_ID} from StarredNotes fn) ORDER BY ${Constants.TIMESTAMP} DESC")
    fun queryAllFavoriteStarred(): Flowable<List<Note>>

    @Query("select * from ${Constants.TABLE_NOTES} nl where nl.${Constants.NOTE_ID} in (select fn.${Constants.NOTE_ID} from FavoriteNote fn) ORDER BY ${Constants.TIMESTAMP} DESC")
    fun queryAllFavorite(): Flowable<List<Note>>

    @Query("select * from ${Constants.TABLE_NOTES} nl where nl.${Constants.NOTE_ID} in (select fn.${Constants.NOTE_ID} from StarredNotes fn) ORDER BY ${Constants.TIMESTAMP} DESC")
    fun queryAllStarred(): Flowable<List<Note>>

    @Query("SELECT * FROM ${Constants.TABLE_NOTES} WHERE ${Constants.NOTE_ID}=:arg0")
    fun query(expenseId: Long):Flowable<Note>

    @Insert(onConflict = REPLACE)
    fun insert(favorite: FavoriteNote)

    @Delete
    fun delete(favorite: FavoriteNote)

    @Query("SELECT CASE WHEN EXISTS( SELECT * FROM FavoriteNote where ${Constants.NOTE_ID}=:arg0 ) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    fun isFavorite(noteId: Long): Single<Boolean>

    @Insert(onConflict = REPLACE)
    fun insert(favorite: StarredNotes)

    @Delete
    fun delete(favorite: StarredNotes)

    @Query("SELECT CASE WHEN EXISTS( SELECT * FROM StarredNotes where ${Constants.NOTE_ID}=:arg0 ) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    fun isStarred(noteId: Long): Single<Boolean>

}