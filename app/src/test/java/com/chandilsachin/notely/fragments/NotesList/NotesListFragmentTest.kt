package com.chandilsachin.notely.fragments.NotesList

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import android.view.MenuItem
import android.widget.ImageView
import com.chandilsachin.notely.BuildConfig
import com.chandilsachin.notely.MainActivity
import com.chandilsachin.notely.R
import com.chandilsachin.notely.dagger.AppContext
import com.chandilsachin.notely.dagger.DaggerDatabaseComponent
import com.chandilsachin.notely.dagger.MyApplication
import com.chandilsachin.notely.fragments.NotesDetails.NotesDetailsFragment
import com.chandilsachin.notely.fragments.TestDatabaseModule
import com.chandilsachin.notely.util.TestUtilFunctions
import com.chandilsachin.personal_finance.database.NoteDatabase
import junit.framework.Assert.*
import kotlinx.android.synthetic.main.layout_notes_details.*
import kotlinx.android.synthetic.main.layout_notes_list.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil

@RunWith(PowerMockRunner::class)
@PowerMockRunnerDelegate(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
@PrepareForTest(Context::class, MenuItem::class)
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class NotesListFragmentTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        TestUtilFunctions.overrideRxJavaPlugins()
        NoteDatabase.TEST_MODE = true

        val context = Mockito.mock(Context::class.java)

        val testModule = TestDatabaseModule()
        val component = DaggerDatabaseComponent.builder().databaseModule(testModule)
                .appContext(AppContext(context)).build()
        MyApplication.component = component
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test_check_if_list_fragment_loaded() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        var listFragment = activity.supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        assertTrue(listFragment is NotesListFragment)
        assertNotNull(listFragment)
    }

    @Test
    fun test_make_a_note() {
        var listFragment = NotesListFragment()
        SupportFragmentTestUtil.startVisibleFragment(listFragment, MainActivity::class.java, R.id.fragmentContainer)
        assertEquals(listFragment.rvNotesList.childCount, 0)

        val detailsFragment = NotesDetailsFragment.newInstance(-1)
        SupportFragmentTestUtil.startVisibleFragment(detailsFragment, MainActivity::class.java, R.id.fragmentContainer)
        detailsFragment.richEditorNote.title = "Sample title 1"
        detailsFragment.richEditorNote.html = "Sample content"

        val menuItem = Mockito.mock(MenuItem::class.java)
        Mockito.`when`(menuItem.getItemId()).thenReturn(R.id.menu_item_save)

        detailsFragment.onOptionsItemSelected(menuItem)

        listFragment = NotesListFragment()
        SupportFragmentTestUtil.startVisibleFragment(listFragment, MainActivity::class.java, R.id.fragmentContainer)

        assertEquals(1, listFragment.mAdapter?.itemCount)
    }

    @Test
    fun test_do_star_bookmark_apply_filter() {
        var listFragment = NotesListFragment()
        SupportFragmentTestUtil.startVisibleFragment(listFragment, MainActivity::class.java, R.id.fragmentContainer)
        assertEquals(listFragment.rvNotesList.childCount, 0)

        createNote("Title 1", "Content 1")
        createNote("Title 2", "Content 2")
        createNote("Title 3", "Content 3")
        createNote("Title 4", "Content 4")
        createNote("Title 5", "Content 5")

        listFragment = NotesListFragment()
        SupportFragmentTestUtil.startVisibleFragment(listFragment, MainActivity::class.java, R.id.fragmentContainer)

        assertEquals(5, listFragment.mAdapter?.itemCount)

        listFragment.rvNotesList.getChildAt(0).findViewById<ImageView>(R.id.imageViewFavorite)
                .performClick()
        listFragment.rvNotesList.getChildAt(2).findViewById<ImageView>(R.id.imageViewFavorite)
                .performClick()

        listFragment.rvNotesList.getChildAt(0).findViewById<ImageView>(R.id.imageViewStar)
                .performClick()

        var menuItem = Mockito.mock(MenuItem::class.java)
        Mockito.`when`(menuItem.getItemId()).thenReturn(R.id.menu_item_filterNote)
        listFragment.onOptionsItemSelected(menuItem)

        // Filter bookmarked notes
        listFragment.rvNavigation.getChildAt(0).performClick()
        listFragment.buttonApply.performClick()

        assertEquals(2, listFragment.mAdapter?.itemCount)

        listFragment.ivFilterCancel.performClick()
        assertEquals(5, listFragment.mAdapter?.itemCount)

        menuItem = Mockito.mock(MenuItem::class.java)
        Mockito.`when`(menuItem.getItemId()).thenReturn(R.id.menu_item_filterNote)
        listFragment.onOptionsItemSelected(menuItem)

        // Filter starred notes
        listFragment.rvNavigation.getChildAt(1).performClick()
        listFragment.buttonApply.performClick()
        assertEquals(1, listFragment.mAdapter?.itemCount)
        // un-star note
        listFragment.rvNotesList.getChildAt(0).findViewById<ImageView>(R.id.imageViewStar)
                .performClick()
        listFragment.buttonApply.performClick()
        assertEquals(0, listFragment.mAdapter?.itemCount)

        listFragment.ivFilterCancel.performClick()

        menuItem = Mockito.mock(MenuItem::class.java)
        Mockito.`when`(menuItem.getItemId()).thenReturn(R.id.menu_item_filterNote)
        listFragment.onOptionsItemSelected(menuItem)

        // Filter Starred and Favorites both
        listFragment.rvNavigation.getChildAt(0).performClick()
        listFragment.rvNavigation.getChildAt(1).performClick()
        listFragment.buttonApply.performClick()
        assertEquals(2, listFragment.mAdapter?.itemCount)

        listFragment.ivFilterCancel.performClick()

        assertEquals(5, listFragment.mAdapter?.itemCount)
    }

    private fun createNote(title: String, content: String){
        val detailsFragment = NotesDetailsFragment.newInstance(-1)
        SupportFragmentTestUtil.startVisibleFragment(detailsFragment, MainActivity::class.java, R.id.fragmentContainer)
        detailsFragment.richEditorNote.title = title
        detailsFragment.richEditorNote.html = content

        val menuItem = Mockito.mock(MenuItem::class.java)
        Mockito.`when`(menuItem.getItemId()).thenReturn(R.id.menu_item_save)

        detailsFragment.onOptionsItemSelected(menuItem)
    }

}