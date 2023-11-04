package data.notes

interface NotesDataSource {
    suspend fun getNotes(): List<Note>
    suspend fun createNote(note: NoteCreateInput): Note
    suspend fun updateNote(note: NoteUpdateInput): Note
    suspend fun deleteNote(noteId: String)
}