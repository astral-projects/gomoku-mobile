package gomoku.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.background.Background
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.domain.HeaderLogo
import gomoku.ui.components.generic.Form
import gomoku.ui.components.generic.SubmitButtonWithImage
import gomoku.ui.containers.InputFieldData
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R


@Composable
fun HomeScreen(backgroundConfig: BackgroundConfig) {
    Background(
        config = backgroundConfig,
        header = { HeaderLogo() }
    ) {

        Form(
            title = "Welcome Back User...",
            inputFieldsData = listOf(
                InputFieldData("Find a Match",R.drawable.play_button),
                InputFieldData("LeadersBoard",R.drawable.leaderboard),
                InputFieldData("About",R.drawable.about),
                InputFieldData("Logout" ,R.drawable.door_out)
            ),
            renderInputField = { inputData ->
                SubmitButtonWithImage(text = inputData.text, iconId = inputData.iconId, onClick = { /* Ação aqui */ })
            }
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