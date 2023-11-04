package gomoku.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import pdm.gomoku.R

private const val LOGO_ROTATION_DEGREES = 45f

@Composable
fun GomokuLogo() {
    Image(
        painterResource(R.drawable.gomoku),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .rotate(LOGO_ROTATION_DEGREES)
    )
}