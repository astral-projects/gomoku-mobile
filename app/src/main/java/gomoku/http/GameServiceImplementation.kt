package gomoku.http

import gomoku.Lobby.Lobby
import gomoku.game.GameService
import gomoku.game.domain.Game
import gomoku.game.domain.moves.Move
import gomoku.login.UserInfo
import gomoku.variant.domain.VariantConfig

/**
 * Actual implementation of the JokesService, which fetches jokes from the pre-configured
 * set of remote joke providers.
 * @param providers The list of joke providers
 */
class GameServiceImplementation(
    private val providers: List<GameService>
) : GameService {

    override suspend fun fetchGame(): Game {
        val index = providers.indices.random()
        return providers[index].fetchGame()
    }

    override suspend fun fetchGameById(id: String): Game? {
        val index = providers.indices.random()
        return providers[index].fetchGameById(id)
    }

    override suspend fun createGame(
        variant: VariantConfig,
        lobby: Lobby,
        userInfo: UserInfo
    ): Game {
        TODO("Not yet implemented")
    }

    override suspend fun findGame(variant: VariantConfig, userInfo: UserInfo): Game? {
        val index = providers.indices.random()
        return providers[index].findGame(variant, userInfo)
    }

    override suspend fun makeMove(gameId: Int, moVe: Move): Game {
        TODO("Not yet implemented")
    }
    
}