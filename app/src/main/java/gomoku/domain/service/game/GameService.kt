package gomoku.domain.service.game

import gomoku.domain.game.Game
import gomoku.domain.game.Lobby
import gomoku.domain.game.moves.Move
import gomoku.domain.login.UserInfo
import gomoku.domain.variant.VariantConfig

/**
 * Contract of the service that provides jokes
 */
interface GameService {
    /**
     * Fetches a game from the service
     */
    suspend fun fetchGame(): Game

    suspend fun fetchGameById(id: String): Game?

    suspend fun createGame(variant: VariantConfig, lobby: Lobby, userInfo: UserInfo): Game
    suspend fun findGame(variant: VariantConfig, userInfo: UserInfo): Game?

    suspend fun makeMove(gameId: Int, move: Move): Game

}