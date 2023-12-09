package gomoku.ui.game.components.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import gomoku.domain.game.board.Board
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square

/**
 * Represents the container that wraps the game board.
 * @param modifier the modifier to be applied to the container.
 * @param board board to be displayed.
 * @param localPlayer the local player.
 * @param onCellClick callback to be called when a cell is clicked.
 */
@Composable
fun BoardContainer(
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