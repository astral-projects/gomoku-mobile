package gomoku.game.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gomoku.game.domain.Timer
import gomoku.game.domain.board.Board
import gomoku.game.domain.board.BoardSize
import gomoku.game.domain.board.BoardTurn
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import gomoku.game.domain.moves.move.Square

// Config
private val BoardBorderWidth = 1.dp
private val BoardPadding = 2.dp
private const val MINUS_ONE = -1
private fun isLastRow(rowIndex: Int, boardSize: Int) = rowIndex == boardSize
private fun isLastColumn(columnIndex: Int, boardSize: Int) = columnIndex == boardSize
private fun isFirstRow(rowIndex: Int) = rowIndex == MINUS_ONE
private fun isFirstColumn(columnIndex: Int) = columnIndex == MINUS_ONE
private fun isCorner(rowIndex: Int, columnIndex: Int, boardSize: Int) =
    (isFirstRow(rowIndex) && isFirstColumn(columnIndex)) ||
            (isFirstRow(rowIndex) && isLastColumn(columnIndex, boardSize)) ||
            (isLastRow(rowIndex, boardSize) && isFirstColumn(columnIndex)) ||
            (isLastRow(rowIndex, boardSize) && isLastColumn(columnIndex, boardSize))

/**
 * Board view that will be shown in the game screen.
 * @param board board information.
 * @param localPlayer local player information.
 * @param onCellClick callback that will be called when a cell is clicked.
 * @param maxWidth the maximum width the board can have.
 */
@Composable
fun BoardView(
    board: Board,
    localPlayer: Player,
    onCellClick: (toSquare: Square) -> Unit,
    maxWidth: Dp,
) {
    val boardSize = board.size.value
    val lineColor = MaterialTheme.colorScheme.onPrimary
    var selectedCell by remember { mutableStateOf<Square?>(null) }
    val cellSize = ((maxWidth - BoardBorderWidth * 2 - BoardPadding * 2) / (boardSize + 1))
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .border(BoardBorderWidth, MaterialTheme.colorScheme.outline)
            .padding(BoardPadding)
    ) {
        Column {
            for (rowIndex in MINUS_ONE..boardSize)
                Row {
                    for (columnIndex in MINUS_ONE..boardSize) {
                        when {
                            isCorner(rowIndex, columnIndex, boardSize) ->
                                Box(modifier = Modifier.size(cellSize / 2))

                            isFirstRow(rowIndex) -> DrawVerticalLine(
                                lineColor = lineColor,
                                cellSize = cellSize
                            )

                            isLastRow(rowIndex, boardSize) -> DrawVerticalLine(
                                lineColor = lineColor,
                                cellSize = cellSize
                            )

                            isFirstColumn(columnIndex) -> DrawHorizontalLine(
                                lineColor = lineColor,
                                cellSize = cellSize
                            )

                            isLastColumn(columnIndex, boardSize) -> DrawHorizontalLine(
                                lineColor = lineColor,
                                cellSize = cellSize
                            )

                            else -> {
                                val square = Square(rowIndex, columnIndex)
                                val previousSquare = selectedCell
                                CellView(
                                    cellSize = cellSize,
                                    lineColor = lineColor,
                                    selectedCell = selectedCell == square,
                                    piece = board.getPiece(square),
                                    onClick = {
                                        if (board.isPlayerTurn(localPlayer)) {
                                            selectedCell = square
                                            if (previousSquare == selectedCell) {
                                                onCellClick(square)
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
        }
    }
}

@Composable
fun DrawVerticalLine(
    lineColor: Color,
    cellSize: Dp
) {
    Box(
        modifier = Modifier
            .height(cellSize / 2)
            .width(cellSize)
            .drawBehind {
                drawLine(
                    color = lineColor,
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.width),
                    strokeWidth = 2f * density
                )
            }
    )
}

@Composable
fun DrawHorizontalLine(
    lineColor: Color,
    cellSize: Dp
) {
    Box(
        modifier = Modifier
            .height(cellSize)
            .width(cellSize / 2)
            .drawBehind {
                drawLine(
                    color = lineColor,
                    start = Offset(0f, size.width),
                    end = Offset(size.width, size.width),
                    strokeWidth = 2f * density
                )
            }
    )
}

@Composable
@Preview
fun BoardViewPreview() {
    val moves = mapOf(
        Square(0, 0) to Piece(Player.W),
        Square(0, 1) to Piece(Player.B),
        Square(1, 0) to Piece(Player.W),
        Square(1, 1) to Piece(Player.B),
        Square(2, 0) to Piece(Player.W),
        Square(2, 1) to Piece(Player.B),
        Square(3, 0) to Piece(Player.W),
        Square(3, 1) to Piece(Player.B),
        Square(4, 0) to Piece(Player.W),
        Square(4, 1) to Piece(Player.B)
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BoardView(
            board = Board(
                moves = moves,
                turn = BoardTurn(Player.W, Timer(0, 10)),
                size = BoardSize.FIFTEEN
            ),
            localPlayer = Player.W,
            onCellClick = {},
            maxWidth = 380.dp
        )
    }
}