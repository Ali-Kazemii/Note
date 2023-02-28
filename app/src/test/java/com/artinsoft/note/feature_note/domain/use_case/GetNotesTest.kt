package com.artinsoft.note.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.artinsoft.note.feature_note.data.repository.FakeNoteRepository
import com.artinsoft.note.feature_note.domain.model.Note
import com.artinsoft.note.feature_note.domain.util.NoteOrder
import com.artinsoft.note.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest {

    private lateinit var getNotes: GetNotes
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        getNotes = GetNotes(fakeNoteRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index,
                    id = index + 1
                )
            )
        }
        notesToInsert.shuffle()
        notesToInsert.forEach {
            runBlocking {
                fakeNoteRepository.insertNote(it)
            }
        }
    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking {
        val notes = getNotes(noteOrder = NoteOrder.Title(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking {
        val notes = getNotes(noteOrder = NoteOrder.Title(OrderType.Descending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
        }
    }
    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking {
        val notes = getNotes(noteOrder = NoteOrder.Date(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timestamp).isLessThan(notes[i + 1].timestamp)
        }
    }
    @Test
    fun `Order notes by date descending, correct order`() = runBlocking {
        val notes = getNotes(noteOrder = NoteOrder.Date(OrderType.Descending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timestamp).isGreaterThan(notes[i + 1].timestamp)
        }
    }
    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking {
        val notes = getNotes(noteOrder = NoteOrder.Color(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isLessThan(notes[i + 1].color)
        }
    }
    @Test
    fun `Order notes by color descending, correct order`() = runBlocking {
        val notes = getNotes(noteOrder = NoteOrder.Color(OrderType.Descending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
        }
    }

}