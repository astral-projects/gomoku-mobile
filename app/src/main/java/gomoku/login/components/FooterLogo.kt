package gomoku.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Config
private val logoPadding = 25.dp
private const val LOGO_WIDTH_FACTOR = 0.5f

@Composable
fun FooterLogo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(logoPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        GomokuLogo(modifier = Modifier
            .weight(1f)
            .fillMaxWidth(LOGO_WIDTH_FACTOR))
    }
}
