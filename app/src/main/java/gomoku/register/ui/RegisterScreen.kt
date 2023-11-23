package gomoku.register.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import gomoku.LoadState
import gomoku.Loaded
import gomoku.home.domain.Home.gameName
import gomoku.login.User
import gomoku.register.ui.components.FooterBubbles
import gomoku.shared.background.Background
import gomoku.shared.components.HeadlineText
import gomoku.shared.theme.GomokuTheme


/**
 * Represents the register screen main composable.
 * @param registerState the register state.
 * @param onCreateUser callback to be executed when the submit button is clicked.
 */
@Composable
fun RegisterScreen(
    registerState: LoadState<User>,
    onCreateUser: (username: String, email: String, password: String, confirmPassword: String) -> Unit,
) {
    GomokuTheme {
        Background(
            header = { HeadlineText(text = stringResource(gameName)) },
            footer = { FooterBubbles() }
        ) {
            RegisterView(registerState.toRegisterScreenState(), onCreateUser)
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview2() {
    RegisterScreen(
        Loaded(Result.success(User(1, "email", "password", "confirmPassword"))),
        { _, _, _, _ -> })
}

