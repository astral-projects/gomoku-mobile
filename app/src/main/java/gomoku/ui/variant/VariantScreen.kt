package gomoku.ui.variant

import androidx.compose.runtime.Composable
import gomoku.domain.IOState
import gomoku.domain.login.UserInfo
import gomoku.domain.variant.VariantConfig
import gomoku.ui.variant.components.VariantView

/**
 * Represents the Variant screen main composable.
 * @param gameMatchState the [IOState] of the game match to be displayed in the body.
 * @param variantsState the [IOState] of the variants to be displayed in the body.
 * @param onPlayRequest the callback to be called when the play button is clicked.
 * @param toLeaderboardScreen callback to be executed when the user clicks on the respective navigation item.
 * @param toAboutScreen callback to be executed when the user clicks on the respective navigation item.
 * @param onLogoutRequest callback to be executed when the user clicks on the respective navigation item.
 */
@Composable
fun VariantScreen(
    state: VariantScreenState,
    userInfo: UserInfo,
    isDarkTheme: Boolean? = false,
    setDarkTheme: (Boolean) -> Unit,
    onPlayRequest: (variantConfig: VariantConfig) -> Unit,
    onLobbyExitRequest: () -> Unit,
    toLeaderboardScreen: () -> Unit,
    toAboutScreen: () -> Unit,
    onLogoutRequest: () -> Unit,
) {
    VariantView(
        userInfo = userInfo,
        inDarkTheme = isDarkTheme ?: false,
        variantScreenState = state,
        onPlayRequest = onPlayRequest,
        onLobbyExitRequest = onLobbyExitRequest,
        setDarkTheme = setDarkTheme,
        toLeaderboardScreen = toLeaderboardScreen,
        toAboutScreen = toAboutScreen,
        onLogoutRequest = onLogoutRequest,
    )
}
