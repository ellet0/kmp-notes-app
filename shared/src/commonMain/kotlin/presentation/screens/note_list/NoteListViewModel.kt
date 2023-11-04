package presentation.screens.note_list

import data.notes.Note
import data.notes.NoteCreateInput
import data.notes.NoteUpdateInput
import data.notes.NotesDataService
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NoteListUiState(
    val notes: List<Note> = emptyList(),
    val isGridList: Boolean = false,
    val isLoading: Boolean = false
)

class NoteListViewModel : ViewModel() {
    private val notesService = NotesDataService
    private val _uiState = MutableStateFlow(
        NoteListUiState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        getNotes()
    }

    fun setIsLoading(value: Boolean) {
        _uiState.update {
            it.copy(isLoading = value)
        }
    }

    fun toggleIsGridList() {
        _uiState.update {
            it.copy(isGridList = !it.isGridList)
        }
    }

    private fun getNotes() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val notes = notesService.getNotes()
            _uiState.update {
                it.copy(notes = notes, isLoading = false)
            }
        }
    }

    fun createNote(input: NoteCreateInput) {
        viewModelScope.launch {
            val note = notesService.createNote(input)
            println(note)
            _uiState.update {
                val newNotes = it.notes.toMutableList()
                newNotes.add(0, note)
                it.copy(notes = newNotes)
            }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            notesService.deleteNote(noteId)
            _uiState.update {
                val newNotes = it.notes.toMutableList()
                val note = newNotes.firstOrNull { note -> note.id == noteId }
                    ?: throw NullPointerException("We couldn't find the note by the note id")
                val noteIndex = newNotes.indexOf(note)
                newNotes.removeAt(noteIndex)

                it.copy(notes = newNotes)
            }
        }
    }

    fun updateNote(input: NoteUpdateInput) {
        viewModelScope.launch {
            val newNote = notesService.updateNote(input)
            _uiState.update { uiState ->
                val newNotes = uiState.notes.toMutableList()
                val currentNote = uiState.notes.firstOrNull { it.id == input.id }
                    ?: throw NullPointerException("We couldn't find the current note by the note id")
                val noteIndex = uiState.notes.indexOf(currentNote)
                newNotes.removeAt(noteIndex)
                newNotes.add(noteIndex, newNote)

                uiState.copy(notes = newNotes.toList())
            }
        }
    }

}