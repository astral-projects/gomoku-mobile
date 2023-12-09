package gomoku.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import gomoku.domain.home.ButtonData
import gomoku.domain.home.Home
import gomoku.ui.login.components.HeaderLogo
import gomoku.ui.shared.background.Background
import gomoku.ui.shared.components.Form
import gomoku.ui.shared.components.IconButton
import gomoku.ui.shared.theme.GomokuTheme
import pdm.gomoku.R

private val HomeScreenTestTagFindMatch = "Find a mach"
private val HomeScreenTestTagLeaderBoard = "Leaderboard"
private val HomeScreenTestTagAbout = "About"
private val HomeScreenTestTagLogout = "Logout"

/**
 * Represents the Home screen main composable.
 * @param inDarkTheme if the theme is dark or not.
 * @param username username of the logged user.
 * @param onFindMatch callback to be executed when the find match button is clicked.
 * @param onLeaderBoard callback to be executed when the leader board button is clicked.
 * @param onAbout callback to be executed when the about button is clicked.
 * @param onLogout callback to be executed when the logout button is clicked.
 */
@Composable
fun HomeScreen(
    inDarkTheme: Boolean?,
    username: String,
    onFindMatch: () -> Unit = {},
    onLeaderBoard: () -> Unit = {},
    onAbout: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    GomokuTheme(darkTheme = inDarkTheme ?: false) {
        Background(
            header = { HeaderLogo() }
        ) {
            Form(
                title = stringResource(id = Home.welcome, username),
                inputFieldsData = listOf(
                    ButtonData(
                        stringResource(Home.findGame),
                        R.drawable.play_button,
                        onClick = onFindMatch
                    ),
                    ButtonData(
                        stringResource(Home.leaderboard),
                        R.drawable.leaderboard,
                        onClick = onLeaderBoard
                    ),
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
    HomeScreen(false, "Admin-".repeat(100))
}