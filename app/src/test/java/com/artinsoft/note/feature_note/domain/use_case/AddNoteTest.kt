package com.artinsoft.note.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.artinsoft.note.feature_note.data.repository.FakeNoteRepository
import com.artinsoft.note.feature_note.domain.model.Note
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteTest{

    lateinit var note: Note
    lateinit var repository: FakeNoteRepository

    @Before
    fun setup(){
        repository = FakeNoteRepository()
        note = Note(
            title = "test",
            content = "content",
            timestamp = 2,
            color = 1,
            id = 1
        )
    }

    @Test
    fun `check title is blank`(){
        assertThat(note.title).isNotEmpty()
    }

    @Test
    fun `check content is blank`(){
        assertThat(note.content).isNotEmpty()
    }

    @Test
    fun `check insert note`() = runBlocking{
        repository.insertNote(note)
        val notes = repository.getNotes()
        assertThat(notes.first()).contains(note)
    }
}