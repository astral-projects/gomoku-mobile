package gomoku.register

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.GAME_NAME
import gomoku.ui.FooterBubbles
import gomoku.ui.background.Background
import gomoku.ui.components.generic.Form
import gomoku.ui.components.generic.TextWithFont
import gomoku.ui.containers.InputFieldData
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R

@Composable
fun RegisterScreen(
    onSubmit: () -> Unit,
) {
    GomokuTheme {
        Background(
            header = { TextWithFont(text = GAME_NAME) },
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
                submitAction = onSubmit,
            )
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        onSubmit = {},
    )
}