package com.artinsoft.note.feature_note.domain.use_case

import com.artinsoft.note.feature_note.data.repository.FakeNoteRepository
import com.artinsoft.note.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteNoteTest {

    private lateinit var deleteNote: DeleteNote
    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var note: Note

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeNoteRepository)

        note = Note(
            title = "test",
            content = "test",
            timestamp = 1,
            color = 1,
            id = 1
        )

        runBlocking {
            fakeNoteRepository.insertNote(note)
        }

    }

    @Test
    fun `delete first item, return true`() = runBlocking{
        deleteNote.invoke(note)
        val list = fakeNoteRepository.getNotes()
        assertThat(list.first()).doesNotContain(note)
    }
}