package com.chandilsachin.notely.fragments.NotesList

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.chandilsachin.notely.dagger.MyApplication
import com.chandilsachin.notely.database.entities.StarredNotes
import com.chandilsachin.personal_finance.database.LocalRepo
import com.chandilsachin.personal_finance.database.entities.FavoriteNote
import com.chandilsachin.personal_finance.database.entities.Note
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by sachin on 28/1/18.
 */
class NotesListViewModel : ViewModel() {

    @Inject
    lateinit var localRepo: LocalRepo;
    val noteLiveData = MutableLiveData<List<Note>>()

    init {
        MyApplication.component.inject(this)
    }

    fun getNotes() {
        localRepo.getAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    noteLiveData.value = it
                },{it.printStackTrace()})
    }

    fun getNotesFavoriteStarred(favorite: Boolean, starred: Boolean) {
        val single = if (favorite && starred)
            localRepo.getAllNotesFavoriteStarred()
        else if (favorite)
            localRepo.getAllNotesFavorite()
        else if (starred)
            localRepo.getAllNotesStarred()
        else
            localRepo.getAllNotes()

        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    noteLiveData.value = it
                }, { it.printStackTrace() })
    }

    fun toggleStar(noteId: Long): Single<Boolean> {
        return localRepo.isStar(noteId)
                .flatMap {
                    if (it) localRepo.removeStar(StarredNotes(noteId))
                    else localRepo.doStar(StarredNotes(noteId))
                }
    }

    fun toggleFavorite(noteId: Long): Single<Boolean> {
        return localRepo.isFavorite(noteId)
                .flatMap {
                    if (it) localRepo.removeFavorite(FavoriteNote(noteId))
                    else localRepo.doFavorite(FavoriteNote(noteId))
                }
    }

    fun isStarred(noteId: Long): Single<Boolean> {
        return localRepo.isStar(noteId)
    }

    fun isFavorite(noteId: Long): Single<Boolean> {
        return localRepo.isFavorite(noteId)
    }

    fun deleteNote(note: Note){
        localRepo.deleteNote(note).subscribe()
    }

}