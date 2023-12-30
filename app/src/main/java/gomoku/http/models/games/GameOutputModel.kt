package gomoku.http.models.games

import gomoku.domain.game.board.Board
import gomoku.domain.game.board.toBoardSize
import gomoku.domain.game.board.toBoardTurn
import gomoku.domain.game.match.Game
import gomoku.domain.game.match.toGameState
import gomoku.domain.game.moves.createMovesFromGrid
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.http.models.users.UserOutputModel
import pdm.gomoku.R

data class GameOutputModel(
    val id: Int,
    val state: StateOutputModel,
    val variant: VariantIdOutputModel,
    val board: BoardOutputModel,
    val createdAt: String,
    val updatedAt: String,
    val hostId: Int,
    val guestId: Int
)


fun GameOutputModel.toGame(user: List<UserOutputModel>): Game {
    val host = user.first { it.id == this.hostId }
    val guest = user.first { it.id == this.guestId }
    val game = Game(
        id = this.id,
        variantId = this.variant.id,
        board = Board(
            createMovesFromGrid(this.board.grid),
            this.board?.turn?.player?.toBoardTurn(),
            winner = if (this.board.winner != null) {
                if (this.board.winner == "W") PlayerInfo(
                    this.hostId,
                    host.username,
                    R.drawable.man
                ) else PlayerInfo(this.guestId, guest.username, R.drawable.man)
            } else null,
            size = this.variant.boardSize.toBoardSize()
        ),
        host = PlayerInfo(this.hostId, host.username, R.drawable.man),
        guest = PlayerInfo(this.guestId, guest.username, R.drawable.man),
        state = this.state.name.toGameState()
    )
    return game
}
