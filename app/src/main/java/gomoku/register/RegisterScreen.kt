package gomoku.register

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import gomoku.home.ui.GAME_NAME
import androidx.compose.ui.unit.dp

import gomoku.ui.background.Background
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.generic.Form
import gomoku.ui.components.generic.InputTextEditor
import gomoku.ui.components.generic.SubmitButton
import gomoku.ui.components.generic.TextWithFont
import gomoku.ui.containers.ButtonData
import gomoku.ui.theme.EggShell
import gomoku.ui.theme.Grey
import pdm.gomoku.R

@Composable
fun RegisterScreen(
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onSubmit: () -> Unit,
) {
    GomokuTheme{
        Background(
            header = { TextWithFont(text = GAME_NAME) },
            footer = {
                FooterBubbles()
            }
        ) {
            //TODO(Change validationParameter to viewModel)
            Form(
                title = "Introduce your data",
                inputFieldsData = listOf(
                    ButtonData("username", R.drawable.user, validationParameter = { text -> text.length >= 5 && text.length <= 30 }),
                    ButtonData("email", R.drawable.email,
                        validationParameter = {
                                email -> val regex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+$".toRegex()
                                regex.matches(email)
                    }),
                    ButtonData("************", R.drawable.lock, validationParameter = { text -> text.length >= 8 && text.length <= 40 }),
                    ButtonData("************", R.drawable.lock, validationParameter = { text -> text.length >= 8 && text.length <= 40 }),
                ),
                footer = {
                    //TODO(nao esquecer meter as vairaveis globais )
                    val boxWidth = backgroundConfig.screenWidth * 0.6f
                    val boxHeight = backgroundConfig.screenHeight * 0.06f
                    SubmitButton(
                        onButtonText = "Sign Up",
                        onClick =  onSubmit ,
                        modifier = Modifier
                            .height(boxHeight)
                            .width(boxWidth),
                        textColor = Color.Black
                    )
                },
            ) { inputFieldData ->
                InputTextEditor(
                    modifier=
                    Modifier
                        .background(EggShell)
                        .border(2.dp, Grey, RectangleShape),
                    text = inputFieldData.label,
                    iconId = inputFieldData.iconId,
                )
            }
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