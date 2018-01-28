package com.chandilsachin.personal_finance.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.chandilsachin.personal_finance.database.Constants
import java.util.*

/**
 * Created by sachin on 22/5/17.
 */
@Entity(tableName = Constants.TABLE_NOTES)
class Note(@ColumnInfo(name = Constants.TITLE) var title: String = "",
           @ColumnInfo(name = Constants.NOTE_TEXT) var noteText: String = "",
           @ColumnInfo(name = Constants.TIMESTAMP) var timestamp: Date = Date()) {

    @ColumnInfo(name = Constants.NOTE_ID)
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0
}
