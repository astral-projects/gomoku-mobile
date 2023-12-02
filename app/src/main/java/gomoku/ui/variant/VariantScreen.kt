package gomoku.ui.variant

import androidx.compose.runtime.Composable
import gomoku.domain.IOState
import gomoku.domain.game.match.Match
import gomoku.domain.getOrNull
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

//TODO(Decide if we pass Boolean or Boolean? as parameter uin dartheme, i think its more correct to pass Boolean? but we need to check)
@Composable
fun VariantScreen(
    gameMatchState: IOState<Match>,
    isDarkTheme: Boolean? = false,
    setDarkTheme: (Boolean) -> Unit,
    variantsState: IOState<List<VariantConfig>>,
    onPlayRequest: (variantConfig: VariantConfig) -> Unit,
    toLeaderboardScreen: () -> Unit,
    toAboutScreen: () -> Unit,
    onLogoutRequest: () -> Unit
) {
    VariantView(
        inDarkTheme = isDarkTheme ?: false,
        variantScreenState = variantScreenState(variantsState, gameMatchState),
        onSubmit = onPlayRequest,
        variants = variantsState.getOrNull() ?: emptyList(),
        setDarkTheme = setDarkTheme,
        toLeaderboardScreen = toLeaderboardScreen,
        toAboutScreen = toAboutScreen,
        onLogoutRequest = onLogoutRequest
    )
}
