package gomoku.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.background.Background
import gomoku.ui.components.domain.HeaderLogo
import gomoku.ui.components.generic.Form
import gomoku.ui.components.generic.SubmitButtonWithImage
import gomoku.ui.containers.InputFieldData
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R


@Composable
fun HomeScreen(
    userName: String = "User",
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
                title = "Welcome Back $userName...",
                inputFieldsData = listOf(
                    InputFieldData("Find a Match", R.drawable.play_button, onClick = onFindMatch),
                    InputFieldData("LeadersBoard", R.drawable.leaderboard, onClick = onLeaderBoard),
                    InputFieldData("About", R.drawable.about, onClick = onAbout),
                    InputFieldData("Logout", R.drawable.door_out, onClick = onLogout)
                ),
                renderInputField = { inputData ->
                    SubmitButtonWithImage(
                        text = inputData.text,
                        iconId = inputData.iconId,
                        onClick = inputData.onClick)
                }
            )
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}