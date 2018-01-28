package com.chandilsachin.personal_finance.database

import android.arch.persistence.room.*
import com.chandilsachin.personal_finance.database.entities.Note
import com.chandilsachin.personal_finance.database.entities.FavoriteNotes
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by sachin on 22/5/17.
 */
@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note): Long

    @Insert
    fun insert(notes: List<Note>): List<Long>

    @Update
    fun update(note: Note): Int

    @Delete
    fun delete(note: Note): Int

    @Query("SELECT * FROM ${Constants.TABLE_NOTES} ORDER BY ${Constants.TIMESTAMP} DESC")
    fun queryAll():Flowable<List<Note>>

    @Query("SELECT * FROM ${Constants.TABLE_NOTES} WHERE ${Constants.NOTE_ID}=:arg0")
    fun query(expenseId: Long):Flowable<Note>
}