package gomoku.shared.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    // TODO(): change colors
    primary = Color.Red,
    onPrimary = Color.Black,
    inversePrimary = Color.White,
    secondary = Color.Magenta,
    tertiary = DarkPurple,
    onSecondary = LightYellow,
    surface = Color.Green,
    background = PaynesGrey,
    outline = Color.White,
    error = DarkRed,
    onError = Wine,
    errorContainer = Wine
)

private val lightColorScheme = lightColorScheme(
    primary = EggShell,
    onPrimary = Color.White,
    inversePrimary = Color.Black,
    secondary = DarkOrange,
    tertiary = DarkPurple,
    onSecondary = LightYellow,
    surface = SilverLakeBlue,
    background = PaynesGrey,
    outline = Color.Black,
    error = DarkRed,
    onError = Wine,
    errorContainer = Wine
)

@Composable
fun GomokuTheme(
    darkTheme: Boolean = false, // = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

