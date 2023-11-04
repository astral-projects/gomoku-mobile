package gomoku.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.login.components.HeaderLogo
import gomoku.ui.background.Background
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.Form
import gomoku.ui.components.HyperLink
import gomoku.ui.components.InputTextEditor
import gomoku.ui.components.SubmitButton
import gomoku.ui.components.TextWithFont
import gomoku.ui.containers.ButtonData
import gomoku.ui.theme.EggShell
import gomoku.ui.theme.GomokuTheme
import gomoku.ui.theme.Grey
import pdm.gomoku.R


/**
 * Main composable of the login screen.
 * @param onSubmit callback to be executed when the submit button is clicked.
 * @param onSignUpClick callback to be executed when the sign-up link is clicked.
 */

@Composable
fun LoginScreen(
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onSubmit: () -> Unit,
    onSignUpClick: (Int) -> Unit,
) {
    GomokuTheme {
        Background(
            header = { HeaderLogo() },
        ) {
            //TODO(Meter estas variaveis globais porque sao usadas em demasiado sitios)
            val boxWidth = backgroundConfig.screenWidth * 0.6f
            val boxHeight = backgroundConfig.screenHeight * 0.06f
            //TODO(Verify if the function validationParameter needs to be in ViewModel or not)
            Form(
                title = "Welcome",
                inputFieldsData = listOf(
                    ButtonData(
                        "username",
                        R.drawable.user,
                        validationParameter = { text -> text.length >= 5 && text.length <= 30 }),
                    ButtonData(
                        "************",
                        R.drawable.lock,
                        validationParameter = { text -> text.length >= 8 && text.length <= 40 }),
                ),
                renderInputField = { inputFieldData ->
                    InputTextEditor(
                        text = inputFieldData.label,
                        modifier=Modifier
                            .background(EggShell)
                            .border(2.dp, Grey, RectangleShape),
                        validateFunctionalInterface = inputFieldData.validationParameter,
                        iconId = inputFieldData.iconId
                    )
                },
                footer = {
                    SubmitButton(
                        onButtonText = "Login",
                        onClick = onSubmit,
                        modifier = Modifier
                            .width(boxWidth)
                            .height(boxHeight)
                    )
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithFont(text = "Don't have an account? ")
                HyperLink(
                    text = " Sign up",
                    onClick = onSignUpClick
                )
            }
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(
        backgroundConfig = BackgroundConfig(LocalConfiguration.current),
        onSubmit = {},
        onSignUpClick = {}
    )
}
