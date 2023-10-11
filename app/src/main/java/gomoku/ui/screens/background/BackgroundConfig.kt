package gomoku.ui.screens.background

import android.content.res.Configuration
import androidx.compose.ui.unit.dp

data class BackgroundConfig(val config: Configuration) {
    val screenHeight = config.screenHeightDp.dp
    val screenWidth = config.screenWidthDp.dp
}
