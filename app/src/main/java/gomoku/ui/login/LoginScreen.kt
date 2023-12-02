package gomoku.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.domain.IOState
import gomoku.domain.Loaded
import gomoku.domain.login.UserInfo
import gomoku.ui.login.components.HeaderLogo
import gomoku.ui.login.components.LoginView
import gomoku.ui.shared.background.Background
import gomoku.ui.shared.theme.GomokuTheme

/**
 * Represents the login screen main composable.
 * @param authenticatedUserInfo indicates whether the user is authenticated.
 * @param onSubmit callback to be executed when the submit button is clicked.
 * @param onSignUpLinkClick callback to be executed when the sign-up link is clicked.
 */
@Composable
fun LoginScreen(
    inDarkTheme: Boolean? = false,
    authenticatedUserInfo: IOState<UserInfo>,
    onSubmit: (username: String, password: String) -> Unit,
    onSignUpLinkClick: (Int) -> Unit
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
    val userInfo = UserInfo(1, "John", "123", "dfqwdew@.com", 3)
    LoginScreen(
        authenticatedUserInfo = Loaded(Result.success(userInfo)),
        onSubmit = { _, _ -> },
        onSignUpLinkClick = { },
    )
}
