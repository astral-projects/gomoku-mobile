package gomoku.ui.variant

import gomoku.domain.IOState
import gomoku.domain.Idle
import gomoku.domain.Loaded
import gomoku.domain.Loading
import gomoku.domain.game.match.Game
import gomoku.domain.game.match.Lobby
import gomoku.domain.game.match.Match
import gomoku.domain.variant.VariantConfig

/**
 * Represents the state of the Variant screen.
 */
enum class VariantScreenState {
    LoadingVariants,
    LoadingGameMatch,
    WaitingInLobby,
    Loaded,
    Error
}

/**
 * Returns the [VariantScreenState] based on the [IOState]s of the variants and the game match.
 */
fun variantScreenState(
    variantsState: IOState<List<VariantConfig>>,
    gameMatchState: IOState<Match>
): VariantScreenState {
    return when {
        variantsState is Loading || variantsState is Idle -> VariantScreenState.LoadingVariants
        gameMatchState is Loading -> VariantScreenState.LoadingGameMatch
        gameMatchState is Loaded && gameMatchState.value.getOrNull() is Lobby -> VariantScreenState.WaitingInLobby
        gameMatchState is Loaded && gameMatchState.value.getOrNull() is Game -> VariantScreenState.Loaded
        else -> VariantScreenState.Error
    }
}