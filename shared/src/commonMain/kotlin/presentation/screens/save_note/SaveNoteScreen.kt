package presentation.screens.save_note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import data.notes.Note
import data.notes.NoteCreateInput
import data.notes.NoteUpdateInput
import logic.services.share.ShareService
import presentation.screens.note_list.NoteListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveNoteScreen(
    viewModel: NoteListViewModel,
    shareService: ShareService,
    noteToEdit: Note?,
    onRequestNavigateBack: () -> Unit
) {
    var noteTitle by remember { mutableStateOf(noteToEdit?.title ?: "") }
    var noteText by remember { mutableStateOf(noteToEdit?.text ?: "") }

    fun onSubmit() {
        if (noteToEdit != null) {
            viewModel.updateNote(
                NoteUpdateInput(
                    title = noteTitle,
                    text = noteText,
                    id = noteToEdit.id
                )
            )
            onRequestNavigateBack()
            return
        }
        viewModel.createNote(
            NoteCreateInput(
                title = noteTitle,
                text = noteText,
            )
        )
        onRequestNavigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("${if (noteToEdit == null) "Add" else "Update"} Note")
                },
                actions = {
                    IconButton(
                        enabled = noteTitle.isNotBlank() && noteText.isNotBlank(),
                        onClick = {
                            shareService.shareText(
                                subject = noteTitle,
                                text = noteText
                            )
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share note"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                noteTitle,
                onValueChange = { noteTitle = it },
                label = {
                    Text("Title")
                },
                placeholder = {
                    Text("Enter a title for your note")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
                isError = noteTitle.isBlank(),
                supportingText = {
                    if (noteTitle.isBlank()) {
                        Text("Please enter a title for your note")
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                noteText,
                onValueChange = { noteText = it },
                label = {
                    Text("Text")
                },
                placeholder = {
                    Text("Enter a text for your note")
                },
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onSubmit() }
                ),
                isError = noteText.isBlank(),
                supportingText = {
                    if (noteText.isBlank()) {
                        Text("Please enter a text for your note")
                    }
                }
            )
            Spacer(Modifier.height(16.dp))
            Row {
                ElevatedButton(
                    onClick = {
                        onRequestNavigateBack()
                    },
                ) {
                    Text("Cancel")
                }
                Spacer(Modifier.width(8.dp))
                OutlinedButton(
                    enabled = noteTitle.isNotBlank() && noteText.isNotBlank(),
                    onClick = ::onSubmit,
                ) {
                    Text("Save")
                }
            }
        }
    }

}