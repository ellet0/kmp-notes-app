package presentation.screens.note_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.notes.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteGridItem(
    sharedConfigurations: NoteItemConfigurations
) {
    Card(
        modifier = Modifier.padding(16.dp),
        onClick = { sharedConfigurations.onClick(sharedConfigurations.note) },
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = sharedConfigurations.note.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                IconButton(
                    onClick = {
                        sharedConfigurations.onDelete(sharedConfigurations.note.id)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete note icon",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            Text(
                text = sharedConfigurations.note.text,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}