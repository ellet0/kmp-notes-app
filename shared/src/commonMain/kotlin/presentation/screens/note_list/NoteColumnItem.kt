package presentation.screens.note_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import data.notes.Note

@Composable
fun NoteColumnItem(
    sharedConfigurations: NoteItemConfigurations
) {
    ListItem(
        modifier = Modifier.clickable {
            sharedConfigurations.onClick(sharedConfigurations.note)
        },
        leadingContent = {
            // Similar to CircleAvatar from FreshNotes app
            Box(
                modifier = Modifier.size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.onPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text((sharedConfigurations.index + 1).toString())
            }
        },
        headlineContent = {
            Text(sharedConfigurations.note.text)
        },
        overlineContent = {
            Text(sharedConfigurations.note.title)
        },
        trailingContent = {
            IconButton(
                onClick = {
                    sharedConfigurations.onDelete(sharedConfigurations.note.id)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete note",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}