package gomoku.ui.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import gomoku.domain.game.Timer
import gomoku.domain.game.board.Board
import gomoku.domain.game.board.BoardSize
import gomoku.domain.game.board.BoardTurn
import gomoku.domain.game.match.Game
import gomoku.domain.game.moves.Move
import gomoku.domain.game.moves.move.Piece
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.domain.variant.OpeningRule
import gomoku.domain.variant.VariantConfig
import gomoku.domain.variant.VariantName
import gomoku.ui.game.components.board.BoardContainer
import gomoku.ui.game.components.chips.GameInfoChip
import gomoku.ui.game.components.chips.PlayerInfoChip
import gomoku.ui.shared.components.DismissButton
import gomoku.ui.shared.components.SkeletonLoader
import pdm.gomoku.R

/**
 * Represents the game view.
 * @param localPlayer the local player.
 * @param onLeaveGameRequest callback to be called when the user wants to leave the game.
 * @param onCellClick callback to be called when a cell is clicked.
 * @param game the game to be displayed.
 * @param isLoading if the game is loading.
 */
@Composable
fun GameView(
    playerInfo: PlayerInfo,
    localPlayer: Player,
    onLeaveGameRequest: () -> Unit,
    onCellClick: (toSquare: Square) -> Unit,
    game: Game,
    isLoading: Boolean = false
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            SkeletonLoader(loading = isLoading) {
                GameInfoChip(
                    leadingIconId = R.drawable.timer,
                    label = "${game.board.turn.timer}"
                )
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            SkeletonLoader(loading = isLoading) {
                PlayerInfoChip(
                    playerInfo = playerInfo,
                    trailingIconId = R.drawable.white_circle,
                    select = game.board.turn.player == Player.W
                )
            }
        }
        SkeletonLoader(loading = isLoading) {
            BoardContainer(
                board = game.board,
                localPlayer = localPlayer,
                onCellClick = onCellClick,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SkeletonLoader(loading = isLoading) {
                PlayerInfoChip(
                    playerInfo = game.host,
                    trailingIconId = R.drawable.black_circle,
                    select = game.board.turn.player == Player.B
                )
            }
            SkeletonLoader(loading = isLoading) {
                GameInfoChip(
                    leadingIconId = R.drawable.directions,
                    label = "${game.board.moves.size}"
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SkeletonLoader(loading = isLoading) {
                DismissButton(
                    onButtonText = stringResource(id = R.string.game_leave_button_text),
                    enable = !isLoading,
                    onDismiss = onLeaveGameRequest
                )
            }
        }
    }
}

@Preview
@Composable
private fun GameViewPreview() {
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
    val board = Board(
        moves = moves,
        turn = turn,
        size = BoardSize.NINETEEN
    )
    GameView(
        playerInfo = PlayerInfo("Player W", R.drawable.man5),
        localPlayer = Player.W,
        onLeaveGameRequest = {},
        onCellClick = {},
        game = Game(
            id = "1",
            variant = VariantConfig(
                id = 1,
                name = VariantName.CARO,
                openingRule = OpeningRule.LONG_PRO,
                boardSize = BoardSize.NINETEEN
            ),
            board = board,
            host = PlayerInfo("Player W", R.drawable.man),
            guest = PlayerInfo("Player B", R.drawable.man2),
        )
    )
}