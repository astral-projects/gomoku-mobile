package gomoku.login

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.background.Background
import gomoku.ui.components.domain.HeaderLogo
import gomoku.ui.components.generic.Form
import gomoku.ui.components.generic.TextWithFont
import gomoku.ui.containers.InputFieldData
import gomoku.ui.components.generic.HyperLink
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R


/**
 * Composable of the Login Screen
 * @param onSubmit - action to be executed when the submit (login) button is clicked
 * @param onSignUpClick - action to be executed when the sign up link is clicked
 */
@Composable
fun LoginScreen(
    onSubmit: () -> Unit,
    onSignUpClick: (Int) -> Unit,
) {
    GomokuTheme {
        Background(
            header = { HeaderLogo() },
        ) {
            Form(
                title = "Welcome",
                inputFieldsData = listOf(
                    InputFieldData("username", R.drawable.user),
                    InputFieldData("************", R.drawable.lock),
                ),
                submitButtonText = "Login",
                submitAction = onSubmit
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
        onSubmit = {},
        onSignUpClick = {}
    )
}