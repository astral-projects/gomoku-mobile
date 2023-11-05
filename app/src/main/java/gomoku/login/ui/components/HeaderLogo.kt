package gomoku.login.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.home.domain.Home.GAME_NAME
import gomoku.ui.components.HeadlineText

// Config
private val logoPadding = 25.dp
private val logoTextSpacerWidth = 20.dp

/**
 * The app's logo in the header.
 */
@Composable
fun HeaderLogo() {
    Column(
        modifier = Modifier
            .padding(logoPadding)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GomokuLogo(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(logoTextSpacerWidth))
        HeadlineText(text = GAME_NAME)
    }
}

@Preview
@Composable
private fun SmallerHeaderLogo() {
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
private fun LargerHeaderLogo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 400.dp)
    ) {
        HeaderLogo()
    }
}