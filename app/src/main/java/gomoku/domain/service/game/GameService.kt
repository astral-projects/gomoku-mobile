package gomoku.domain.service.game

import gomoku.domain.game.match.Game
import gomoku.domain.game.match.Match
import gomoku.domain.game.moves.Move
import gomoku.domain.login.UserInfo
import gomoku.domain.service.game.errors.FetchGameException
import gomoku.domain.service.game.errors.FetchLobbyException

/**
 * Defines the behavior of a game service.
 */
interface GameService {

    /**
     * Retrieves a game with the specified id.
     * @param id The id of the game to retrieve.
     * @return The game with the specified id.
     * @throws FetchGameException If the game with the given id cannot be found.
     */
    @Throws(FetchGameException::class)
    suspend fun fetchGameById(id: Int): Game

    /**
     * Attempts to find a game with the specified variant configuration
     * for a player.
     * If a game cannot be found, a new lobby is created.
     * @param variantId The variant id for the game to find.
     * @param token The user token for the logged user that is searching the game.
     * @return The match which was found or created.
     */
    suspend fun findGame(variantId: Int, userInfo: UserInfo): Match

    /**
     * Attempts to make a move in the game with the specified id.
     * @param gameId The id of the game to make a move in.
     * @param move The move to make.
     * @return The updated game after the move has been made.
     * @throws FetchGameException If the game with the given id cannot be found.
     */
    @Throws(FetchGameException::class)
    suspend fun makeMove(gameId: Int, move: Move, token: String): Game

    /**
     * Attempts to exit the lobby with the specified id.
     * @param lobbyId The id of the lobby to exit.
     * @param token The user token for the player exiting the lobby.
     * @throws FetchLobbyException If the lobby with the given id cannot be found,
     * or the player is not in the lobby.
     */
    @Throws(FetchLobbyException::class)
    suspend fun exitLobby(lobbyId: Int, token: String)

    /**
     * Attempts to retrieve the information to know if the player is
     * waiting in a lobby.
     * @param lobbyId The id of the lobby to check.
     * @return True if the player is waiting in the lobby, false otherwise.
     */
    suspend fun waitingInLobby(lobbyId: Int, token: String): Pair<Boolean, Int>

    /**
     * Attempts to exit the game with the specified id.
     * @param gameId The id of the game to exit.
     * @param token The user token for the player exiting the game.
     * @throws FetchGameException If the game with the given id cannot be found,
     * or the player is not in the game.
     */
    suspend fun exitGame(gameId: Int, token: String)
}
