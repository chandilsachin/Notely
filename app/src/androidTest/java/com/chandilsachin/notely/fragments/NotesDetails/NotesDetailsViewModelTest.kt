package com.chandilsachin.notely.fragments.NotesDetails

import android.support.test.runner.AndroidJUnit4
import com.chandilsachin.notely.util.TestUtilFunctions
import com.chandilsachin.personal_finance.database.NoteDatabase
import com.chandilsachin.personal_finance.database.entities.Note
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotesDetailsViewModelTest {

    lateinit var viewModel: NotesDetailsViewModel

    @Before
    fun setUp() {
        TestUtilFunctions.overrideRxJavaPlugins()
        NoteDatabase.TEST_MODE = true

        viewModel = NotesDetailsViewModel()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun addNote() {
        val note = Note("Sample Title1", "Sample Content1")
        note.noteId = 1
        viewModel.addNote(note)

        viewModel.getNote(1)
        assertEquals("Sample Title1", viewModel.noteLiveData.value?.title)
    }

    @Test
    fun getNote() {

    }

}