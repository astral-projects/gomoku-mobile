package gomoku.ui.screens.backgrounds

import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pdm.gomoku.R

/**
 * Class that contains all the background resources
 * @param configuration the configuration of the device
 * @param screenHeight the height of the screen
 * @param screenWidth the width of the screen
 * @param gomokuLogo the logo of the game
 */
class BackGround(
    configuration: Configuration,
    val screenHeight: Dp = configuration.screenHeightDp.dp,
    val screenWidth: Dp = configuration.screenWidthDp.dp,
    val gomokuLogo: Int = R.drawable.gomoku
)