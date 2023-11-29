package gomoku.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import gomoku.domain.LoadState
import gomoku.domain.Loaded
import gomoku.domain.home.Home.gameName
import gomoku.ui.register.components.FooterBubbles
import gomoku.ui.register.components.RegisterView
import gomoku.ui.shared.background.Background
import gomoku.ui.shared.components.HeadlineText
import gomoku.ui.shared.theme.GomokuTheme

/**
 * Represents the register screen main composable.
 * @param registerState the register state.
 * @param onCreateUser callback to be executed when the submit button is clicked.
 */
@Composable
fun RegisterScreen(
    registerState: LoadState<Int>,
    onCreateUser: (username: String, email: String, password: String) -> Unit,
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
        registerState = Loaded(Result.success(1)),
        onCreateUser = { _, _, _ -> }
    )
}

