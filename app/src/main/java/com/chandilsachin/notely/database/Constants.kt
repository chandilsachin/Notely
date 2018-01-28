package com.chandilsachin.personal_finance.database

/**
 * Created by sachin on 6/6/17.
 */
open class Constants{
    companion object{

        // Tables
        const val TABLE_NOTES = "notes_list"
        const val TABLE_EXPENSES_SYNC = "expenses_sync"

        // Columns
        const val NOTE_ID = "note_id"
        const val TITLE = "title"
        const val NOTE_TEXT = "note_text"
        const val TIMESTAMP = "timestamp"

        const val TOTAL_SPEND = "total_spend"
        const val MONTH = "month"

        const val SYNC = "sync"
    }
}