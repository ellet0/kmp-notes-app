import androidx.compose.runtime.Composable
import data.notes.Note
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.serialization.json.Json
import logic.services.share.ShareService
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import presentation.AppTheme
import presentation.AppThemeConfigurations
import presentation.screens.note_list.NoteListScreen
import presentation.screens.note_list.NoteListViewModel
import presentation.screens.save_note.SaveNoteScreen
import presentation.utils.navigation.Screens

@Composable
fun App(
    appThemeConfigurations: AppThemeConfigurations,
    shareService: ShareService
) {
    PreComposeApp {
        AppTheme(
            configurations = appThemeConfigurations,
        ) {

            val navigator = rememberNavigator()
            val noteListViewModel = getViewModel(Unit, viewModelFactory { NoteListViewModel() })
            NavHost(
                navigator = navigator,
                initialRoute = Screens.NoteList.name
            ) {
                scene(Screens.NoteList.name) {
                    NoteListScreen(
                        viewModel = noteListViewModel,
                        onAddNoteClicked = {
                            navigator.navigate(
                                route = Screens.SaveNote.SaveNewNote.name,
                            )
                        },
                        onNoteClicked = { note ->
                            navigator.navigate(
                                Screens.SaveNote.SaveExistingNote(
                                    Json.encodeToString(
                                        Note.serializer(),
                                        note
                                    )
                                ).name,
                            )
                        }
                    )
                }
                scene(Screens.SaveNote.name) { backStackEntry ->
                    val noteJson: String? = backStackEntry.path<String>("note")
                    val note: Note? = noteJson?.let {
                        Json.decodeFromString(
                            Note.serializer(),
                            it
                        )
                    }

                    SaveNoteScreen(
                        viewModel = noteListViewModel,
                        shareService = shareService,
                        noteToEdit = note,
                        onRequestNavigateBack = { navigator.goBack() }
                    )
                }
            }
        }
    }
}

expect fun getPlatformName(): String