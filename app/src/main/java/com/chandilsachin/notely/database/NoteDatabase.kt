package com.chandilsachin.personal_finance.database
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.chandilsachin.notely.database.entities.StarredNotes
import com.chandilsachin.personal_finance.database.entities.FavoriteNote
import com.chandilsachin.personal_finance.database.entities.Note

/**
 * Created by sachin on 22/5/17.
 */

@Database(entities = arrayOf(Note::class, FavoriteNote::class, StarredNotes::class),
        version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun foodDao(): NoteDao

    companion object{
        @JvmStatic
        var TEST_MODE = false
        private val databaseName = "expense"

        private var db: NoteDatabase? = null
        private var dbInstance: NoteDao? = null
        fun getInstance(context:Context): NoteDao {
            if(dbInstance == null){
                if(TEST_MODE){
                    db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).allowMainThreadQueries().build()
                    dbInstance = db?.foodDao()
                }

                else {
                    db = Room.databaseBuilder(context, NoteDatabase::class.java, databaseName).build()
                    dbInstance = db?.foodDao()
                }
            }
            return dbInstance!!;
        }

        private fun close(){
            db?.close()
        }
    }
}
