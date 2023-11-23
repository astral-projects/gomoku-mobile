package gomoku.variant.ui

import gomoku.Idle
import gomoku.LoadState
import gomoku.Loading
import gomoku.game.domain.Game
import gomoku.variant.domain.VariantConfig

/**
 * Represents the state of the Variant screen.
 */
enum class VariantScreenState {
    LoadingVariants,
    LoadingGameMatch,
    Loaded,
    Error
}

/**
 * Returns the [VariantScreenState] based on the [LoadState]s of the variants and the game match.
 */
fun variantScreenState(
    variantsState: LoadState<List<VariantConfig>?>,
    gameMatchState: LoadState<Game?>
): VariantScreenState {
    return when {
        variantsState is Loading || variantsState is Idle -> VariantScreenState.LoadingVariants
        gameMatchState is Loading -> VariantScreenState.LoadingGameMatch
        variantsState is Error || gameMatchState is Error -> VariantScreenState.Error
        else -> VariantScreenState.Loaded
    }
}