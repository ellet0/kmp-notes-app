import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.ComposeUIViewController
import logic.services.share.IosShareService
import platform.UIKit.UIViewController
import presentation.AppThemeConfigurations

actual fun getPlatformName(): String = "iOS"

fun MainViewController(): UIViewController = ComposeUIViewController {
    App(
        appThemeConfigurations = AppThemeConfigurations(
            isDarkTheme = isSystemInDarkTheme(),
        ),
        shareService = IosShareService()
    )
}