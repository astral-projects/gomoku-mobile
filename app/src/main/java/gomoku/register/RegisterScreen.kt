package gomoku.register

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import gomoku.home.ui.GAME_NAME
import gomoku.ui.background.Background
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.generic.Form
import gomoku.ui.components.generic.InputTextEditor
import gomoku.ui.components.generic.SubmitButton
import gomoku.ui.components.generic.TextWithFont
import gomoku.ui.containers.InputFieldData
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R

@Composable
fun RegisterScreen(
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
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
                footer = {
                    //TOOD(nao esquecer meter as vairaveis globais )
                    val boxWidth = backgroundConfig.screenWidth * 0.6f
                    val boxHeight = backgroundConfig.screenHeight * 0.04f
                    SubmitButton(
                        onButtonText = "Register",
                        onClick =  onSubmit ,
                        modifier = Modifier
                            .height(boxHeight)
                            .width(boxWidth),
                        letterColor = Color.Black
                    )
                },
            ) { inputFieldData ->
                InputTextEditor(
                    text = inputFieldData.text,
                    iconId = inputFieldData.iconId,
                    backgroundConfig = BackgroundConfig(LocalConfiguration.current),
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