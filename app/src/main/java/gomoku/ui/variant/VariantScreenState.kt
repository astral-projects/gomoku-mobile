package gomoku.ui.variant

import gomoku.domain.variant.VariantConfig

/**
 * Represents the state of the Variant screen.
 */
sealed class VariantScreenState {
    data object Idle : VariantScreenState()
    data class FetchVariants(
        val variants: List<VariantConfig> = emptyList(),
        val isDone: Boolean = false,
    ) : VariantScreenState()

    data class FindGame(val variantId: Int) : VariantScreenState()
    data class WaitingInLobby(val lobbyId: Int, val isPolling: Boolean = false) :
        VariantScreenState()

    data class JoinGame(val gameId: Int) : VariantScreenState()
    data object ExitLobby : VariantScreenState()
    data class Error(val error: Throwable) : VariantScreenState()
    data object LoggingOut : VariantScreenState()
    data object Logout : VariantScreenState()
}