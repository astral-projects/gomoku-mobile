package gomoku.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import gomoku.SkeletonLoaderGeneric
import gomoku.game.domain.Game
import gomoku.game.domain.GameTag
import gomoku.game.domain.Timer
import gomoku.game.domain.board.Board
import gomoku.game.domain.board.BoardSize
import gomoku.game.domain.board.BoardTurn
import gomoku.game.domain.moves.Move
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import gomoku.game.domain.moves.move.Square
import gomoku.game.ui.components.board.BoardView
import gomoku.game.ui.components.chips.GameInfoChip
import gomoku.game.ui.components.chips.PlayerInfoChip
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.shared.components.DismissButton
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
            SkeletonLoaderGeneric(loading = isLoading) {
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
            SkeletonLoaderGeneric(loading = isLoading) {
                PlayerInfoChip(
                    playerInfo = playerInfo,
                    trailingIconId = R.drawable.white_circle,
                    select = game.board.turn.player == Player.W
                )
            }
        }

        SkeletonLoaderGeneric(loading = isLoading) {
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
            SkeletonLoaderGeneric(loading = isLoading) {
                PlayerInfoChip(
                    playerInfo = game.blackPlayer,
                    trailingIconId = R.drawable.black_circle,
                    select = game.board.turn.player == Player.B
                )
            }
            SkeletonLoaderGeneric(loading = isLoading) {
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
            SkeletonLoaderGeneric(loading = isLoading) {
                DismissButton(
                    onButtonText = stringResource(id = GameTag.gameLeaveButtonText),
                    enable = !isLoading,
                    onDismiss = onLeaveGameRequest
                )
            }
        }
    }
}


/**
 * Represents the board container.
 * @param modifier the modifier to be applied to the container.
 * @param board board to be displayed.
 * @param localPlayer the local player.
 * @param onCellClick callback to be called when a cell is clicked.
 */
@Composable
private fun BoardContainer(
    modifier: Modifier = Modifier,
    board: Board,
    localPlayer: Player,
    onCellClick: (toSquare: Square) -> Unit
) {
    var actualWidth by remember { mutableIntStateOf(0) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                actualWidth = coordinates.size.width
            },
        horizontalArrangement = Arrangement.Center
    ) {
        BoardView(
            board = board,
            localPlayer = localPlayer,
            onCellClick = onCellClick,
            maxWidth = LocalDensity.current.run { actualWidth.toDp() }
        )
    }
}

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


@Preview
@Composable
private fun GameViewPreview() {
    GameView(
        playerInfo = PlayerInfo("Player W", R.drawable.man5),
        localPlayer = Player.W,
        onLeaveGameRequest = {},
        onCellClick = {},
        game = Game(
            "I got nothing",
            "https://www.example.com",
            "FREESTYLE",
            board,
            "DSSDSDDS",
            "WEQEWQWEWQE",
            PlayerInfo("Player W", R.drawable.man5),
            PlayerInfo("Player B", R.drawable.woman2)
        )
    )
}