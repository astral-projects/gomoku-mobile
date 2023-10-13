import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.GAME_NAME
import gomoku.ui.components.generic.Form
import gomoku.ui.containers.InputFieldData
import gomoku.ui.background.Background
import gomoku.ui.background.BackgroundConfig
import gomoku.register.FooterBubbles
import pdm.gomoku.R

@Composable
fun RegisterScreen(backgroundConfig: BackgroundConfig) {
    Background(
        config = backgroundConfig,
        header = { Text(text = GAME_NAME, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge) },
        footer = {
            FooterBubbles()
        }
    ) {
        Form(
            title = "Introduce your data",
            inputFieldsData = listOf(
                InputFieldData("username", R.drawable.user),
                InputFieldData("expl@domain.com", R.drawable.email),
                InputFieldData("************", R.drawable.lock),
                InputFieldData("************", R.drawable.lock),
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