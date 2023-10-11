package gomoku.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.screens.HeaderLogo
import gomoku.ui.screens.background.Background
import gomoku.ui.screens.background.BackgroundConfig

@Composable
fun HomeScreen(backgroundConfig: BackgroundConfig) {
    Background(
        config = backgroundConfig,
        header = { HeaderLogo() }
    ) {
        // TODO()
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        backgroundConfig = BackgroundConfig(LocalConfiguration.current)
    )
}