package data.notes

import dev.gitlive.firebase.firestore.DoubleAsTimestampSerializer
import kotlinx.serialization.Serializable

@Serializable
data class NoteCreateInput(
    val title: String,
    val text: String,
    @Serializable(with = DoubleAsTimestampSerializer::class)
    val createdAt: Double = DoubleAsTimestampSerializer.serverTimestamp,
    @Serializable(with = DoubleAsTimestampSerializer::class)
    val updatedAt: Double = DoubleAsTimestampSerializer.serverTimestamp,
)

@Serializable
data class NoteUpdateInput(
    val id: String,
    val title: String,
    val text: String,
    @Serializable(with = DoubleAsTimestampSerializer::class)
    val updatedAt: Double = DoubleAsTimestampSerializer.serverTimestamp,
)

@Serializable
data class Note(
    val id: String,
    val title: String,
    val text: String,
    val createdAt: Long,
    val updatedAt: Long,
) {
    companion object {
        fun dumpNote() = Note(
            "noteId",
            "This is my note title",
            "This is my note text",
            0L,
            0L,
        )
    }
}