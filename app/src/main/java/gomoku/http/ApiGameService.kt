package gomoku.http

import com.google.gson.Gson
import gomoku.domain.game.Game
import gomoku.domain.game.Lobby
import gomoku.domain.game.Timer
import gomoku.domain.game.board.Board
import gomoku.domain.game.board.BoardSize
import gomoku.domain.game.board.BoardTurn
import gomoku.domain.game.moves.Move
import gomoku.domain.game.moves.move.Piece
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.domain.login.UserInfo
import gomoku.domain.service.game.GameService
import gomoku.domain.variant.VariantConfig
import okhttp3.OkHttpClient
import pdm.gomoku.R

class GomokuGame(
    private val client: OkHttpClient,
    private val gson: Gson
) : GameService {

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

    private data class GameDto(
        val id: String,
        val state: String,
        val variant: String,
        val board: String,
        val createdAt: String,
        val updatedAt: String,
        val hostId: Int,
        val guestId: Int
    ) {
        fun toGame(): Game {
            val turn = BoardTurn(
                player = Player.W,
                timer = Timer(0, 55)
            )
            val moves = mapOf(
                Move(Square(1, 'a'), Piece(Player.W)),
                Move(Square(4, 'b'), Piece(Player.B)),
                Move(Square(3, 'c'), Piece(Player.W)),
                Move(Square(7, 'd'), Piece(Player.B)),
                Move(Square(5, 'e'), Piece(Player.W)),
                Move(Square(3, 'f'), Piece(Player.B)),
            )
            val board2 = Board(
                moves = moves,
                turn = turn,
                size = BoardSize.NINETEEN
            )

            return Game(
                id,
                state,
                variant,
                board2,
                createdAt,
                updatedAt,
                PlayerInfo("Player W", R.drawable.man5),
                PlayerInfo("Player B", R.drawable.woman2)
            )
        }
    }
}
