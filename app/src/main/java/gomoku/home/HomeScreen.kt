package gomoku.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.background.Background
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.domain.HeaderLogo
import gomoku.ui.containers.InputButtonWithImage
import gomoku.ui.components.generic.Form_Home_Menu
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R


@Composable
fun HomeScreen(backgroundConfig: BackgroundConfig) {
    Background(
        config = backgroundConfig,
        header = { HeaderLogo() }
    ) {

        Form_Home_Menu(
            title = "Welcome Back User...",
            inputFieldsData = listOf(
                InputButtonWithImage("Find a Match",R.drawable.play_button),
                InputButtonWithImage("LeadersBoard",R.drawable.leaderboard),
                InputButtonWithImage("About",R.drawable.about),
                InputButtonWithImage("Logout" ,R.drawable.door_out)
            )
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    GomokuTheme {
        HomeScreen(
            backgroundConfig = BackgroundConfig(LocalConfiguration.current)
        )
    }

}