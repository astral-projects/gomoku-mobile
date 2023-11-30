package gomoku.ui.variant

import gomoku.domain.IOState
import gomoku.domain.Idle
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
 * Returns the [VariantScreenState] based on the [IOState]s of the variants and the game match.
 */
fun variantScreenState(
    variantsState: IOState<List<VariantConfig>?>,
    gameMatchState: IOState<Game?>
): VariantScreenState {
    return when {
        variantsState is Loading || variantsState is Idle -> VariantScreenState.LoadingVariants
        gameMatchState is Loading -> VariantScreenState.LoadingGameMatch
        variantsState is Error || gameMatchState is Error -> VariantScreenState.Error
        else -> VariantScreenState.Loaded
    }
}