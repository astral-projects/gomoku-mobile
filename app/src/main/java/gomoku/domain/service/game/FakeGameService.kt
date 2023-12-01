package gomoku.domain.service.game

import gomoku.domain.game.Game
import gomoku.domain.game.Lobby
import gomoku.domain.game.moves.Move
import gomoku.domain.login.UserInfo
import gomoku.domain.variant.VariantConfig

/**
 * Fake implementation of the GameService. It will replaced by a real implementation
 * in a future lecture.
 */
object FakeGameService : GameService {
    override suspend fun fetchGame(): Game {
        TODO("Not yet implemented")
    }

    override suspend fun fetchGameById(id: String): Game? {
        TODO("Not yet implemented")
    }

    override suspend fun createGame(
        variant: VariantConfig,
        lobby: Lobby,
        userInfo: UserInfo
    ): Game {
        TODO("Not yet implemented")
    }

    override suspend fun findGame(variant: VariantConfig, userInfo: UserInfo): Game? {
        TODO("Not yet implemented")
    }

    override suspend fun makeMove(gameId: Int, move: Move): Game {
        TODO("Not yet implemented")
    }

}