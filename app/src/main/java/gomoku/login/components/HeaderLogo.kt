package gomoku.login.components

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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gomoku.home.domain.Home.GAME_NAME
import pdm.gomoku.R

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
        GomokuLogo()
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
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp)
    ) {
        HeaderLogo()
    }
}

@Preview
@Composable
fun LargerHeaderLogo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 400.dp)
    ) {
        HeaderLogo()
    }
}