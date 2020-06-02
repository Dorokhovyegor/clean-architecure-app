package com.codingwithmitch.cleannotes.framework.datasource.network.abstraction

import com.codingwithmitch.cleannotes.business.domain.model.Note

interface NoteFirestoreService {

    suspend fun insertOrUpdate(note: Note)

    suspend fun deleteNote(primaryKey: String)

    suspend fun insertDeletedNote(note: Note)

    suspend fun insertDeletedNotes(notes: List<Note>)

    suspend fun deleteDeletedNote(note: Note)

    suspend fun getDeletedNote(): List<Note>

    suspend fun deleteAllNotes()

    suspend fun searchNote(note: Note): Note?

    suspend fun getAllNotes(): List<Note>

    suspend fun insertOrUpdateNotes(notes: List<Note>)

}