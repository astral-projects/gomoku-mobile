package gomoku.login.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import pdm.gomoku.R

private const val LOGO_ROTATION_DEGREES = 45f

/**
 * The Gomoku logo.
 * @param modifier The modifier to be applied to the logo.
 */
@Composable
fun GomokuLogo(modifier: Modifier = Modifier) {
    Image(
        painterResource(R.drawable.gomoku),
        contentDescription = null,
        modifier = modifier
            .rotate(LOGO_ROTATION_DEGREES)
    )
}