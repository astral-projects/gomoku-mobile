package gomoku.ui.variant

import gomoku.domain.Idle
import gomoku.domain.LoadState
import gomoku.domain.Loading
import gomoku.domain.game.Game
import gomoku.domain.variant.VariantConfig

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