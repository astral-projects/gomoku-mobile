package gomoku.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pdm.gomoku.R

private const val LOGO_ROTATION_DEGREES = 45f
private val logoPadding = 25.dp

@Composable
fun HeaderLogo() {
    Column(
        modifier = Modifier
            .padding(logoPadding)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.gomoku),
            contentDescription = null,
            modifier = Modifier
                .weight(1f) // fills the entire width
                .rotate(LOGO_ROTATION_DEGREES)
        )
        Text(
            text = GAME_NAME,
            fontSize = 30.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.varelaround_regular,
                    FontWeight.Bold
                )
            ),
            modifier = Modifier
                .padding(top = 20.dp)
        )
    }
}

@Preview
@Composable
fun SmallerHeaderLogo() {
    Box(
        modifier = Modifier.fillMaxWidth().heightIn(max = 200.dp)
    ) {
        HeaderLogo()
    }
}


@Preview
@Composable
fun LargerHeaderLogo() {
    Box(
        modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp)
    ) {
        HeaderLogo()
    }
}