package gomoku.shared.background

import android.content.res.Configuration
import androidx.compose.ui.unit.dp

/**
 * Represents the configuration of the background.
 * @property screenHeight the height of the screen.
 * @property screenWidth the width of the screen.
 */
class BackgroundConfig(config: Configuration) {
    val screenHeight = config.screenHeightDp.dp
    val screenWidth = config.screenWidthDp.dp
}
