package com.chandilsachin.personal_finance.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.chandilsachin.personal_finance.database.Constants

/**
 * Created by sachin on 31/12/17.
 */
@Entity(primaryKeys = arrayOf(Constants.NOTE_ID))
class FavoriteNote(@ColumnInfo(name = Constants.NOTE_ID) var noteId: Long = 0) {

}