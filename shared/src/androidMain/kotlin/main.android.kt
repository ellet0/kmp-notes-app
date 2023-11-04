import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import logic.services.share.AndroidShareService
import presentation.AppThemeConfigurations

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = App(
    appThemeConfigurations = AppThemeConfigurations(
        isDarkTheme = isSystemInDarkTheme()
    ),
    shareService = AndroidShareService(
        LocalContext.current,
    )
)
