package gomoku.ui.variant

import androidx.compose.runtime.Composable
import gomoku.domain.IOState
import gomoku.domain.game.Game
import gomoku.domain.getOrNull
import gomoku.domain.variant.VariantConfig
import gomoku.ui.variant.components.VariantView

/**
 * Represents the Variant screen main composable.
 * @param gameMatchState the [IOState] of the [Game] to be displayed in the body.
 * @param variants the [List] of [VariantConfig]s to be displayed in the body.
 * @param onSubmit the callback to be called when the submit button is clicked.
 * @param toLeaderboardScreen callback to be executed when the user clicks on the respective navigation item.
 * @param toAboutScreen callback to be executed when the user clicks on the respective navigation item.
 * @param onLogoutRequest callback to be executed when the user clicks on the respective navigation item.
 */
@Composable
fun VariantScreen(
    gameMatchState: IOState<Game?>,
    isDarkTheme: Boolean? = false,
    variantsState: IOState<List<VariantConfig>?>,
    onSubmit: (variantConfig: VariantConfig) -> Unit,
    setDarkTheme: (Boolean) -> Unit,
    toLeaderboardScreen: () -> Unit,
    toAboutScreen: () -> Unit,
    onLogoutRequest: () -> Unit
) {
    VariantView(
        inDarkTheme = isDarkTheme ?: false,
        variantScreenState = variantScreenState(variantsState, gameMatchState),
        onSubmit = onSubmit,
        variants = variantsState.getOrNull() ?: emptyList(),
        setDarkTheme = setDarkTheme,
        toLeaderboardScreen = toLeaderboardScreen,
        toAboutScreen = toAboutScreen,
        onLogoutRequest = onLogoutRequest
    )
}
