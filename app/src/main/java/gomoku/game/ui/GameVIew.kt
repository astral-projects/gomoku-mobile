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
 * Represents the game view composable.
 * @param localPlayer the [Player] that is playing locally.
 * @param onLeaveGameRequest the callback to be called when the dismiss button is clicked.
 * @param onCellClick the callback to be called when a cell is clicked.
 * @param game the [Game] to be displayed.
 */
@Composable
fun GameView(
    localPlayer: Player,
    onLeaveGameRequest: () -> Unit,
    onCellClick: (toSquare: Square) -> Unit,
    game: Game
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
            GameInfoChip(
                leadingIconId = R.drawable.timer,
                label = "${game.board.turn.timer}"
            )

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            PlayerInfoChip(
                playerInfo = game.whitePlayer,
                trailingIconId = R.drawable.white_circle,
                select = game.board.turn.player == Player.W
            )
        }

        BoardContainer(
            board = game.board,
            localPlayer = localPlayer,
            onCellClick = onCellClick,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PlayerInfoChip(
                playerInfo = game.blackPlayer,
                trailingIconId = R.drawable.black_circle,
                select = game.board.turn.player == Player.B
            )
            GameInfoChip(
                leadingIconId = R.drawable.directions,
                label = "${game.board.moves.size}"
            )
        }
        // Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            DismissButton(
                onButtonText = stringResource(id = GameTag.gameLeaveButtonText),
                enable = true,
                onDismiss = onLeaveGameRequest
            )
        }
    }
}


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