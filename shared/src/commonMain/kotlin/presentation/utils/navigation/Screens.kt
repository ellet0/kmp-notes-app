package presentation.utils.navigation

sealed class Screens(
    val name: String
) {
    data object NoteList : Screens("/noteList")
    data object SaveNote: Screens("/saveNote/{note}?") {
        data object SaveNewNote: Screens("/saveNote")
        data class SaveExistingNote(val noteJson: String): Screens("/saveNote/$noteJson")
    }
}