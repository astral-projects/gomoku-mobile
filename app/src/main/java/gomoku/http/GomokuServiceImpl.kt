package gomoku.http

import gomoku.game.GameService
import gomoku.game.domain.Game

/**
 * Actual implementation of the JokesService, which fetches jokes from the pre-configured
 * set of remote joke providers.
 * @param providers The list of joke providers
 */
class GomokuServiceImpl(
    private val providers: List<GameService>
) : GameService {

    override suspend fun fetchGame(): Game {
        val index = providers.indices.random()
        return providers[index].fetchGame()
    }
    /*
        override suspend fun searchJokes(term: Term): List<Game> {
            val index = providers.indices.random()
            return providers[index].searchJokes(term)
        }*/
}