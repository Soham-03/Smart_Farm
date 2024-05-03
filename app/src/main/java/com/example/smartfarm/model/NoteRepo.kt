package com.example.smartfarm.model

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }
}