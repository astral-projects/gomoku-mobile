package gomoku.domain.service.game

import gomoku.domain.game.Timer
import gomoku.domain.game.board.Board
import gomoku.domain.game.board.BoardTurn
import gomoku.domain.game.match.Game
import gomoku.domain.game.match.Lobby
import gomoku.domain.game.match.Match
import gomoku.domain.game.moves.Move
import gomoku.domain.game.moves.move.Player
import gomoku.domain.login.UserInfo
import gomoku.domain.service.AbstractFakeService
import gomoku.domain.service.game.errors.FetchGameException
import gomoku.domain.service.game.errors.FetchLobbyException
import java.util.UUID

object FakeGameService : GameService, AbstractFakeService() {

    private val turnTimer = Timer(1, 30)
    private var games: MutableList<Game> = mutableListOf()
    private var lobbies: MutableList<Lobby> = mutableListOf(
        Lobby(
            id = generateRandomId(),
            host = guest,
            variantId = variants.first().id
        )
    )

    override suspend fun fetchGameById(id: Int): Game =
        games.findOrThrow(FetchGameException()) { it.id == id }

    override suspend fun findGame(variantId: Int, userInfo: UserInfo): Match {
        val lobby: Lobby? = lobbies.find {
            it.variantId == variantId && it.host.id != userInfo.id
        }
        if (lobby != null) {
            // TODO("keeping lobby for now to avoid having to create a new one")
            // lobbies.remove(lobby)
            val game = Game(
                id = generateRandomId(),
                variant = variants.find { it.id == variantId }!!,
                board = Board(
                    moves = emptyMap(),
                    turn = BoardTurn(Player.W, turnTimer),
                    size = variants.find { it.id == variantId }!!.boardSize
                ),
                host = lobby.host.toPlayerInfo(),
                guest = userInfo.toPlayerInfo()
            )
            games.add(game)
            return game
        } else {
            val createdLobby = Lobby(
                id = generateRandomId(),
                host = userInfo,
                variantId = variantId
            )
            lobbies.add(createdLobby)
            return createdLobby
        }
    }

    override suspend fun makeMove(gameId: Int, move: Move, token: String): Game {
        val game = games.findOrThrow(FetchGameException()) { it.id == gameId }
        val newBoard = game.board.copy(
            moves = game.board.moves + move,
            turn = BoardTurn(game.board.turn.other(), turnTimer)
        )
        return game.copy(board = newBoard)
    }

    override suspend fun exitLobby(lobbyId: Int, token: String) {
        val lobby = lobbies.findOrThrow(FetchLobbyException()) { it.id == lobbyId }
        lobbies.remove(lobby)
    }

    override suspend fun waitingInLobby(lobbyId: Int, token: String): Boolean {
        return lobbies.any { it.id == lobbyId }
    }

    /**
     * Generates a random UUID to be used as an id.
     */
    private fun generateRandomId(): Int = UUID.randomUUID().hashCode()
}
