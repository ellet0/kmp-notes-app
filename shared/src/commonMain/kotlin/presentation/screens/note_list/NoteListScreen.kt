package presentation.screens.note_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.notes.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel,
    onAddNoteClicked: () -> Unit,
    onNoteClicked: (note: Note) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Fresh Notes Lite")
                },
                actions = {
                    PlainTooltipBox(
                        tooltip = {
                            Text(
                                if (uiState.isGridList)
                                    "Use column list" else "Use grid list",
                            )
                        }
                    ) {
                        IconButton(
                            modifier = Modifier.tooltipAnchor(),
                            onClick = {
                                viewModel.toggleIsGridList()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = if (uiState.isGridList)
                                    "Use column list" else "Use grid list"
                            )
                        }
                    }

                    PlainTooltipBox(
                        tooltip = {
                            Text("Add a note")
                        }
                    ) {
                        IconButton(
                            modifier = Modifier.tooltipAnchor(),
                            onClick = onAddNoteClicked,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add note"
                            )
                        }
                    }

                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        AnimatedVisibility(
            visible = uiState.notes.isEmpty() && !uiState.isLoading,
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "You don't have any notes yet."
                )
            }
        }
        AnimatedVisibility(
            visible = uiState.notes.isNotEmpty(),
            modifier = Modifier.padding(paddingValues)
        ) {

            fun sharedOnClick(note: Note) {
                onNoteClicked(note)
            }

            if (uiState.isGridList) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(200.dp),
                ) {
                    items(
                        count = uiState.notes.size
                    ) { index ->
                        var showConfirmDeleteDialog by remember { mutableStateOf(false) }
                        if (showConfirmDeleteDialog)
                            ConfirmDeleteNoteDialog(
                                onConfirmDelete = {
                                    viewModel.deleteNote(uiState.notes[index].id)
                                    showConfirmDeleteDialog = false
                                },
                                onDismissRequest = {
                                    if (showConfirmDeleteDialog) {
                                        showConfirmDeleteDialog = false
                                    }
                                },
                            )
                        NoteGridItem(
                            NoteItemConfigurations(
                                index = index,
                                note = uiState.notes[index],
                                onClick = { note -> sharedOnClick(note) },
                                onDelete = { _ -> showConfirmDeleteDialog = true }
                            )
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(
                        count = uiState.notes.size
                    ) { index ->
                        var showConfirmDeleteDialog by remember { mutableStateOf(false) }
                        if (showConfirmDeleteDialog)
                            ConfirmDeleteNoteDialog(
                                onConfirmDelete = {
                                    viewModel.deleteNote(uiState.notes[index].id)
                                    showConfirmDeleteDialog = false
                                },
                                onDismissRequest = {
                                    if (showConfirmDeleteDialog) {
                                        showConfirmDeleteDialog = false
                                    }
                                },
                            )
                        NoteColumnItem(
                            NoteItemConfigurations(
                                index = index,
                                note = uiState.notes[index],
                                onClick = { note -> sharedOnClick(note) },
                                onDelete = { _ -> showConfirmDeleteDialog = true }
                            )
                        )
                    }
                }
            }
        }
    }
}