package data.notes

object NotesDataService: NotesDataSource {
    private val service: NotesDataSource = FirebaseNotesDataSource()
    override suspend fun getNotes(): List<Note> {
        return service.getNotes()
    }

    override suspend fun createNote(note: NoteCreateInput): Note {
        return service.createNote(note)
    }

    override suspend fun deleteNote(noteId: String) {
        return service.deleteNote(noteId)
    }

    override suspend fun updateNote(note: NoteUpdateInput): Note {
        return service.updateNote(note)
    }
}