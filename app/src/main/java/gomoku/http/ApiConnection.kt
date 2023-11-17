package gomoku.http

import com.google.gson.Gson
import gomoku.game.FetchGameException
import gomoku.game.GameService
import gomoku.game.domain.Game
import gomoku.game.domain.Timer
import gomoku.game.domain.board.Board
import gomoku.game.domain.board.BoardSize
import gomoku.game.domain.board.BoardTurn
import gomoku.game.domain.moves.Move
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import gomoku.game.domain.moves.move.Square
import gomoku.leaderboard.domain.PlayerInfo
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
    //TODO(This file is not being used yet but it will be used in the future, and first of all i want to use the FakeGameService)
    private val request: Request by lazy {
        Request.Builder()
            .url("http://localhost:8080/games/1")
            .addHeader("accept", "application/json")
            //.addHeader("Authorization", "Bearer ImqQMeRr4Tldc2cy3zCoVjSA7n14SHTrf9Nc2YxEGaU=")
            .build()
    }

    override suspend fun fetchGame(): Game =
        suspendCoroutine {
            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(
                        FetchGameException(
                            "Could not fetch joke ${e.message} ",
                            e
                        )
                    )
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null)
                        it.resumeWithException(FetchGameException("Could not fetch joke. Remote service returned ${response.code}"))
                    else {
                        val dto = gson.fromJson(body.string(), GameDto::class.java)
                        it.resumeWith(Result.success(dto.toGame()))
                    }
                }
            })
        }

    //TODO(CHANGE THIS CLASS CAN NOT STAY LIKE THIS)
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
        fun toGame():Game{
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
