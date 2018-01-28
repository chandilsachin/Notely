package com.chandilsachin.personal_finance.database.entities

import android.arch.persistence.room.ColumnInfo
import com.chandilsachin.personal_finance.database.Constants

/**
 * Created by sachin on 31/12/17.
 */
class FavoriteNotes(@ColumnInfo(name = Constants.NOTE_ID) var noteId: Long = 0) {

}