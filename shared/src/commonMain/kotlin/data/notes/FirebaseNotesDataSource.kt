package data.notes

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.FirebaseFirestoreException
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.orderBy
import kotlinx.datetime.Clock

class FirebaseNotesDataSource : NotesDataSource {
    private val notes = Firebase.firestore.collection("notes")
    override suspend fun getNotes(): List<Note> {
        try {
            return notes
                .orderBy(Note::updatedAt.name, Direction.DESCENDING)
                .get()
                .documents
                .map {
                    Note(
                        id = it.id,
                        title = it.get(Note::title.name),
                        text = it.get(Note::text.name),
                        createdAt = it.get<Timestamp>(Note::createdAt.name).seconds,
                        updatedAt = it.get<Timestamp>(Note::updatedAt.name).seconds,
                    )
                }
        } catch (e: FirebaseFirestoreException) {
            throw NoteException(
                message = "Error while getting the notes in the cloud. $e",
                operationType = NoteExceptionOperationType.GetAll
            )
        } catch (e: Exception) {
            throw NoteException(
                message = "Unknown error while getting the notes in the cloud. $e",
                operationType = null
            )
        }
    }

    override suspend fun createNote(note: NoteCreateInput): Note {
        try {

            val newDocument = notes.document
            newDocument.set(
                NoteCreateInput.serializer(),
                note,
                encodeDefaults = true,
            )

            val currentTime = Clock.System.now().epochSeconds

            return Note(
                id = newDocument.id,
                title = note.title,
                text = note.text,
                createdAt = currentTime,
                updatedAt = currentTime,
            )
        } catch (e: FirebaseFirestoreException) {
            throw NoteException(
                message = "Error while creating a note in the cloud.",
                operationType = NoteExceptionOperationType.Create
            )
        } catch (e: Exception) {
            throw NoteException(
                message = "Unknown error while creating a note in the cloud.",
                operationType = null
            )
        }
    }

    override suspend fun deleteNote(noteId: String) {
        try {
            notes.document(noteId).delete()
        } catch (e: FirebaseFirestoreException) {
            throw NoteException(
                message = "Error while deleting a note in the cloud.",
                operationType = NoteExceptionOperationType.Delete
            )
        } catch (e: Exception) {
            throw NoteException(
                message = "Unknown error while deleting a note in the cloud.",
                operationType = null
            )
        }
    }

    override suspend fun updateNote(note: NoteUpdateInput): Note {
        try {
            notes.document(note.id).update(
                NoteUpdateInput.serializer(),
                note,
                encodeDefaults = true,
            )
            val currentTime = Clock.System.now().epochSeconds
            return Note(
                id = note.id,
                title = note.title,
                text = note.text,
                createdAt = currentTime,
                updatedAt = currentTime,
            )
        } catch (e: FirebaseFirestoreException) {
            throw NoteException(
                message = "Error while updating a note in the cloud.",
                operationType = NoteExceptionOperationType.Update
            )
        } catch (e: Exception) {
            throw NoteException(
                message = "Unknown error while updating a note in the cloud.",
                operationType = null
            )
        }
    }
}