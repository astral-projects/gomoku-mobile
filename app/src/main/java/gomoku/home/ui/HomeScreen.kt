package gomoku.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.home.domain.Home
import gomoku.ui.background.Background
import gomoku.ui.components.HeaderLogo
import gomoku.ui.components.Form
import gomoku.ui.components.IconButton
import gomoku.ui.containers.ButtonData
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R

@Composable
fun HomeScreen(
    username: String,
    onFindMatch: () -> Unit = {},
    onLeaderBoard: () -> Unit = {},
    onAbout: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    GomokuTheme {
        Background(
            header = { HeaderLogo() }
        ) {
            Form(
                title = Home.welcome(username),
                inputFieldsData = listOf(
                    ButtonData("Find a Match", R.drawable.play_button, onFindMatch),
                    ButtonData("LeadersBoard", R.drawable.leaderboard, onLeaderBoard),
                    ButtonData("About", R.drawable.about, onAbout),
                    ButtonData("Logout", R.drawable.door_out, onLogout)
                ),
                renderInputField = { inputData ->
                    IconButton(
                        text = inputData.label,
                        iconId = inputData.iconId,
                        onClick = inputData.onClick
                    )
                }
            )
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen("Player A")
}