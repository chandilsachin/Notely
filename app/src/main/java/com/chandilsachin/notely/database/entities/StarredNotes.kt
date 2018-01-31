package com.chandilsachin.notely.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.chandilsachin.personal_finance.database.Constants

/**
 * Created by sachin on 27/1/18.
 */
@Entity(primaryKeys = arrayOf(Constants.NOTE_ID))
class StarredNotes(@ColumnInfo(name = Constants.NOTE_ID) var noteId: Long = 0) {
}