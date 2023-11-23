package gomoku.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.LoadState
import gomoku.Loaded
import gomoku.login.User
import gomoku.login.ui.components.HeaderLogo
import gomoku.shared.background.Background
import gomoku.shared.theme.GomokuTheme


/**
 * Represents the login screen main composable.
 * @param authenticatedUser indicates whether the user is authenticated.
 * @param onSubmit callback to be executed when the submit button is clicked.
 * @param onSignUpLinkClick callback to be executed when the sign-up link is clicked.
 */
@Composable
fun LoginScreen(
    authenticatedUser: LoadState<User>,
    onSubmit: (username: String, password: String) -> Unit,
    onSignUpLinkClick: (Int) -> Unit = {}
) {
    GomokuTheme {
        Background(
            header = { HeaderLogo() },
        ) {


            LoginView(
                screenState = authenticatedUser.toLoginScreenState(),
                onSubmit = onSubmit,
                onSignUpLinkClick = onSignUpLinkClick
            )
        }
    }
}


@Composable
@Preview
private fun LoginScreenPreview() {
    LoginScreen(
        Loaded(Result.success(User(1, "John", "123", "dqwdqw@.com"))),
        onSubmit = { _, _ -> User(1, "John", "123", "dfqwdew@.com") })
}
