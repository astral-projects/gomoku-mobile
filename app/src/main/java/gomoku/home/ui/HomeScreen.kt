package gomoku.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.home.domain.Home
import gomoku.ui.background.Background
import gomoku.ui.components.generic.HeaderLogo
import gomoku.ui.components.generic.Form
import gomoku.ui.components.generic.SubmitButtonWithImage
import gomoku.ui.containers.ButtonData
import gomoku.ui.theme.*
import pdm.gomoku.R


@Composable
fun HomeScreen(
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
                title = Home.WELCOME,
                inputFieldsData = listOf(
                    ButtonData("Find a Match", R.drawable.play_button, onClick = onFindMatch),
                    ButtonData("LeadersBoard", R.drawable.leaderboard, onClick = onLeaderBoard),
                    ButtonData("About", R.drawable.about, onClick = onAbout),
                    ButtonData("Logout", R.drawable.door_out, onClick = onLogout)
                ),
                renderInputField = { inputData ->
                    SubmitButtonWithImage(
                        text = inputData.label,
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