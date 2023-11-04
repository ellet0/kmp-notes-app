package presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

data class AppThemeConfigurations(
    val isDarkTheme: Boolean,
)

@Composable
fun AppTheme(
    configurations: AppThemeConfigurations,
    content: @Composable () -> Unit
) {
    val colorScheme = when(configurations.isDarkTheme) {
        true -> darkColorScheme()
        false -> lightColorScheme()
    }
    MaterialTheme(
        colorScheme = colorScheme
    ) {
        content()
    }
}