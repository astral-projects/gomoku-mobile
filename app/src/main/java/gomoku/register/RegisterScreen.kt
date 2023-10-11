import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.lib.CenteredHeader
import gomoku.ui.GAME_NAME
import gomoku.ui.lib.Form
import gomoku.ui.screens.InputFieldData
import gomoku.ui.screens.background.Background
import gomoku.ui.screens.background.BackgroundConfig
import gomoku.ui.screens.register.FooterBubbles
import pdm.gomoku.R

@Composable
fun RegisterScreen(backgroundConfig: BackgroundConfig) {
    Background(
        config = backgroundConfig,
        header = { CenteredHeader(text = GAME_NAME) },
        footer = {
            FooterBubbles()
            // Text("Already have an account? Log in")
        }
    ) {
        Form(
            title = "Introduce your data",
            inputFieldsData = listOf(
                InputFieldData("username", R.drawable.user),
                InputFieldData("expl@domain.com", R.drawable.email),
                InputFieldData("************", R.drawable.lock),
                InputFieldData("************", R.drawable.email),
            ),
            submitButtonText = "Register",
            backgroundConfig = backgroundConfig
        )
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        backgroundConfig = BackgroundConfig(LocalConfiguration.current)
    )
}