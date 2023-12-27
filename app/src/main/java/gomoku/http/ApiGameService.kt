package gomoku.http

import com.google.gson.Gson
import gomoku.domain.game.Timer
import gomoku.domain.game.board.Board
import gomoku.domain.game.board.BoardSize
import gomoku.domain.game.board.BoardTurn
import gomoku.domain.game.match.Game
import gomoku.domain.game.match.Match
import gomoku.domain.game.moves.Move
import gomoku.domain.game.moves.move.Piece
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.domain.login.UserInfo
import gomoku.domain.service.game.GameService
import gomoku.domain.variant.OpeningRule
import gomoku.domain.variant.VariantConfig
import gomoku.domain.variant.VariantName
import gomoku.http.models.siren.SirenModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import pdm.gomoku.R
import java.io.IOException
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GomokuGame(
    private val client: OkHttpClient,
    private val gson: Gson
) : GameService {
    private val request: Request by lazy {
        Request.Builder()
            .url("https://5e85-89-114-64-92.ngrok-free.app/api/games/55")
            .addHeader("accept", "application/json")
            .build()
    }

    override suspend fun fetchGameById(id: String): Game =
        suspendCoroutine {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(FetchGameException("Could not fetch joke", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null)
                        it.resumeWithException(FetchGameException("Could not fetch game. Remote service returned ${response.code}"))
                    else {
                        println(body.string())
                        val dto = gson.fromJson(body.string(), SirenModel::class.java)
                        it.resumeWith(Result.success((dto.properties as GameDto).toGame()))
                    }
                }
            })
        }

    class FetchGameException(message: String, cause: Throwable? = null) :
        Exception(message, cause)

    override suspend fun findGame(variant: VariantConfig, userInfo: UserInfo): Match {
        TODO("Not yet implemented")
    }

    override suspend fun makeMove(gameId: String, move: Move): Game {
        TODO("Not yet implemented")
    }

    override suspend fun exitLobby(lobbyId: String, userInfo: UserInfo) {
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
                VariantConfig(
                    1,
                    VariantName.FREESTYLE,
                    OpeningRule.LONG_PRO,
                    BoardSize.NINETEEN
                ),
                board2,
                PlayerInfo("Player W", R.drawable.man5),
                PlayerInfo("Player B", R.drawable.woman2)
            )
        }
    }
}