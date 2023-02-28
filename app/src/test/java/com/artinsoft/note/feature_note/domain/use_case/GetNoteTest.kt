package com.artinsoft.note.feature_note.domain.use_case

import com.artinsoft.note.feature_note.data.repository.FakeNoteRepository
import com.artinsoft.note.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteTest {
    private lateinit var getNote: GetNote
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        getNote = GetNote(fakeNoteRepository)

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
    fun `get first note with id, return true`() = runBlocking{
        val note = fakeNoteRepository.getNoteById(1)
        assertThat(note?.title).isEqualTo("a")
    }
}