package gomoku.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.home.domain.ButtonData
import gomoku.home.domain.Home
import gomoku.login.ui.components.HeaderLogo
import gomoku.ui.background.Background
import gomoku.ui.components.Form
import gomoku.ui.components.IconButton
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R

/**
 * Represents the Home screen main composable.
 * @param username username of the logged user.
 * @param onFindMatch callback to be executed when the find match button is clicked.
 * @param onLeaderBoard callback to be executed when the leader board button is clicked.
 * @param onAbout callback to be executed when the about button is clicked.
 * @param onLogout callback to be executed when the logout button is clicked.
 */
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
                    ButtonData("Find a Match", R.drawable.play_button, onClick = onFindMatch),
                    ButtonData("Leaderboard", R.drawable.leaderboard, onClick = onLeaderBoard),
                    ButtonData("About", R.drawable.about, onClick = onAbout),
                    ButtonData("Logout", R.drawable.door_out, onClick = onLogout)
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
private fun HomeScreenPreview() {
    HomeScreen("Admin-".repeat(100))
}