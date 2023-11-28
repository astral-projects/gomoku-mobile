package gomoku.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.LoadState
import gomoku.Loaded
import gomoku.login.UserInfo
import gomoku.login.domain.Username
import gomoku.login.ui.components.HeaderLogo
import gomoku.shared.background.Background
import gomoku.shared.theme.GomokuTheme


/**
 * Represents the login screen main composable.
 * @param authenticatedUserInfo indicates whether the user is authenticated.
 * @param onSubmit callback to be executed when the submit button is clicked.
 * @param onSignUpLinkClick callback to be executed when the sign-up link is clicked.
 */
@Composable
fun LoginScreen(
    inDarkTheme: Boolean? = false,
    authenticatedUserInfo: LoadState<UserInfo>,
    onSubmit: (username: String, password: String) -> Unit,
    onSignUpLinkClick: (Int) -> Unit = {}
) {
    GomokuTheme(darkTheme = inDarkTheme ?: false) {
        Background(
            header = { HeaderLogo() },
        ) {
            LoginView(
                screenState = authenticatedUserInfo.toLoginScreenState(),
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
        authenticatedUserInfo = Loaded(
            Result.success(
                UserInfo(
                    1,
                    Username("John"),
                    "123",
                    "dqwdqw@.com"
                )
            )
        ),
        onSubmit = { _, _ -> UserInfo(1, Username("John"), "123", "dfqwdew@.com") })
}
