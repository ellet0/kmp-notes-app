package data.notes

enum class NoteExceptionOperationType {
    Create,
    GetAll,
    Delete,
    Update
}

data class NoteException(
    override val message: String,
    val operationType: NoteExceptionOperationType?,
): Exception(message)