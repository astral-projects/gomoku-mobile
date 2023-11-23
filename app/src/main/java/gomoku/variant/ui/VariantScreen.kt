package gomoku.variant.ui

import androidx.compose.runtime.Composable
import gomoku.LoadState
import gomoku.game.domain.Game
import gomoku.getOrNull
import gomoku.variant.domain.VariantConfig


/**
 * Represents the Variant screen main composable.
 * @param gameMatchState the [LoadState] of the [Game] to be displayed in the body.
 * @param variants the [List] of [VariantConfig]s to be displayed in the body.
 * @param onSubmit the callback to be called when the submit button is clicked.
 * @param toLeaderboardScreen callback to be executed when the user clicks on the respective navigation item.
 * @param toAboutScreen callback to be executed when the user clicks on the respective navigation item.
 * @param onLogoutRequest callback to be executed when the user clicks on the respective navigation item.
 */
@Composable
fun VariantScreen(
    gameMatchState: LoadState<Game?>,
    variantsState: LoadState<List<VariantConfig>?>,
    onSubmit: (variantConfig: VariantConfig) -> Unit,
    toLeaderboardScreen: () -> Unit,
    toAboutScreen: () -> Unit,
    onLogoutRequest: () -> Unit
) {

    VariantView(
        variantScreenState = variantScreenState(variantsState, gameMatchState),
        onSubmit = onSubmit,
        variants = variantsState.getOrNull() ?: emptyList(),
        toLeaderboardScreen = toLeaderboardScreen,
        toAboutScreen = toAboutScreen,
        onLogoutRequest = onLogoutRequest
    )

}


/*
@Composable
@Preview
private fun VariantScreenPreview() {
    VariantScreen(
        variantsState = ,
        onSubmit = {},
        toLeaderboardScreen = {},
        toAboutScreen = {},
        onLogoutRequest = {}
    )
}

@Composable
@Preview
private fun VariantScreenEmptyPreview() {
    VariantScreen(
        onSubmit = {},
        variants = emptyList(),
        toLeaderboardScreen = {},
        toAboutScreen = {},
        onLogoutRequest = {}
    )
}*/
