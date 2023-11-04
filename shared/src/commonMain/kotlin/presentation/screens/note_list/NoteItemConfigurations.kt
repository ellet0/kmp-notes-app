package presentation.screens.note_list

import data.notes.Note

data class NoteItemConfigurations(
    val index: Int,
    val note: Note,
    val onClick: (note: Note) -> Unit,
    val onDelete: (noteId: String) -> Unit
)
