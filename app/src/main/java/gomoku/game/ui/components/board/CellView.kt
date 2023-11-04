package gomoku.game.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import pdm.gomoku.R

/**
 * Represents a cell in the board.
 * @param cellSize The size of the cell to be drawn.
 * @param lineColor The color of the lines that divide the cell.
 * @param selectedCell Whether the cell is selected or not.
 * @param piece The piece that is in the cell.
 * @param onClick The action to be performed when the cell is clicked.
 */
@Composable
fun CellView(
    cellSize: Dp,
    lineColor: Color,
    selectedCell: Boolean,
    piece: Piece?,
    onClick: () -> Unit,
) {
    val iconId = when (piece?.player) {
        Player.W -> R.drawable.white_circle
        Player.B -> R.drawable.black_circle
        else -> null
    }
    /*var fill by remember(piece?.player) { mutableFloatStateOf(0.1f) }
    LaunchedEffect(piece?.player) {
        if (piece?.player != null) {
            delay(50)
            fill *= 2
        }
        fill = 1f
    }*/
    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(cellSize)
                .drawBehind {
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, size.width / 2),
                        end = Offset(size.width, size.width / 2),
                        strokeWidth = 2f * density
                    )

                    drawLine(
                        color = lineColor,
                        start = Offset(size.width / 2, 0f),
                        end = Offset(size.width / 2, size.width),
                        strokeWidth = 2f * density
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .align(Alignment.Center)
            ) {
                if (iconId != null) {
                    Image(
                        painterResource(id = iconId),
                        contentDescription = null
                    )
                } else {
                    if (selectedCell && piece == null) {
                        Image(
                            painterResource(id = R.drawable.crosshair),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CellViewPreview() {
    CellView(
        cellSize = 50.dp,
        lineColor = Color.Black,
        selectedCell = true,
        piece = null,
        onClick = {}
    )
}
