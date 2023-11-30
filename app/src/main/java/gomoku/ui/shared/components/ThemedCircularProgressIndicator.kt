package gomoku.ui.shared.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Config
private const val PROGRESS_INDICATOR_TRACK_COLOR_OPACITY = 0.5f

/**
 * A circular progress indicator that uses the secondary color scheme color as the indicator color
 * and the inverse primary color scheme color as the track color.
 * @param modifier The modifier to be applied to the progress indicator.
 */
@Composable
fun ThemedCircularProgressIndicator(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.fillMaxHeight(),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = PROGRESS_INDICATOR_TRACK_COLOR_OPACITY)
    )
}
