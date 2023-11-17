package gomoku.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import gomoku.home.domain.ButtonData
import gomoku.home.domain.Home
import gomoku.login.ui.components.HeaderLogo
import gomoku.shared.background.Background
import gomoku.shared.components.Form
import gomoku.shared.components.IconButton
import gomoku.shared.theme.GomokuTheme
import pdm.gomoku.R


private val HomeScreenTestTagFindMatch = "Find a mach"
private val HomeScreenTestTagLeaderBoard = "Leaderboard"
private val HomeScreenTestTagAbout = "About"
private val HomeScreenTestTagLogout = "Logout"

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
                title = stringResource(id = Home.welcome, username),
                inputFieldsData = listOf(
                    ButtonData(stringResource(Home.findGame), R.drawable.play_button, onClick = onFindMatch),
                    ButtonData(stringResource(Home.leaderboard), R.drawable.leaderboard, onClick = onLeaderBoard),
                    ButtonData(stringResource(Home.about), R.drawable.about, onClick = onAbout),
                    ButtonData(stringResource(Home.logout), R.drawable.door_out, onClick = onLogout)
                ),
                renderInputField = { inputData ->
                    IconButton(
                        text = inputData.label,
                        iconId = inputData.iconId,
                        onClick = inputData.onClick,
                        modifier = Modifier.testTag(inputData.label)
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