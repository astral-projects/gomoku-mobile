package gomoku.game.ui.components.board

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
import gomoku.shared.components.shimmerEffect
import gomoku.shared.theme.GomokuTheme

// Constants
private const val FIRST_INDEX = -1

// Config
private val boardBorderWidth = 2.dp
private val boardPadding = 2.dp
private const val LINE_STROKE_WIDTH_FACTOR = 2f

/**
 * Represents the board view.
 * @param board board to be displayed.
 * @param localPlayer the local player.
 * @param onCellClick callback to be called when a cell is clicked.
 * @param maxWidth the maximum width the board can have.
 */
@Composable
fun BoardView(
    modifier: Modifier = Modifier,
    board: Board,
    localPlayer: Player,
    onCellClick: (toSquare: Square) -> Unit,
    maxWidth: Dp,
) {
    val boardSize = board.size.value
    val lineColor = MaterialTheme.colorScheme.surface

    var selectedSquare by remember { mutableStateOf<Square?>(null) }
    val cellSize = ((maxWidth - boardBorderWidth * 2 - boardPadding * 2) / (boardSize + 1))
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .border(boardBorderWidth, MaterialTheme.colorScheme.outline)
            .padding(boardPadding)
    ) {
        Column {
            for (rowIndex in FIRST_INDEX..boardSize)
                Row {
                    for (columnIndex in FIRST_INDEX..boardSize) {
                        when {
                            isCorner(rowIndex, columnIndex, boardSize) ->
                                Box(modifier = modifier.size(cellSize / 2))

                            isFirstRow(rowIndex) -> DrawVerticalLine(
                                modifier = modifier,
                                lineColor = lineColor,
                                cellSize = cellSize
                            )

                            isLastRow(rowIndex, boardSize) -> DrawVerticalLine(
                                modifier = modifier,
                                lineColor = lineColor,
                                cellSize = cellSize
                            )

                            isFirstColumn(columnIndex) -> DrawHorizontalLine(
                                modifier = modifier,
                                lineColor = lineColor,
                                cellSize = cellSize
                            )

                            isLastColumn(columnIndex, boardSize) -> DrawHorizontalLine(
                                modifier = modifier,
                                lineColor = lineColor,
                                cellSize = cellSize
                            )

                            else -> {
                                val square = Square(rowIndex, columnIndex)
                                val previousSquare = selectedSquare
                                CellView(
                                    modifier = modifier,
                                    cellSize = cellSize,
                                    lineColor = lineColor,
                                    selectedCell = selectedSquare == square,
                                    piece = board.getPiece(square)
                                ) {
                                    if (board.isPlayerTurn(localPlayer)) {
                                        selectedSquare = square
                                        if (previousSquare == selectedSquare) {
                                            onCellClick(square)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }
}

@Composable
fun DrawVerticalLine(
    modifier: Modifier = Modifier,
    lineColor: Color,
    cellSize: Dp
) {
    Box(
        modifier = modifier
            .height(cellSize / 2)
            .width(cellSize)
            .drawBehind {
                drawLine(
                    color = lineColor,
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.width / 2),
                    strokeWidth = LINE_STROKE_WIDTH_FACTOR * density
                )
            }
    )
}

@Composable
fun DrawHorizontalLine(
    modifier: Modifier = Modifier,
    lineColor: Color,
    cellSize: Dp
) {
    Box(
        modifier = modifier
            .height(cellSize)
            .width(cellSize / 2)
            .drawBehind {
                drawLine(
                    color = lineColor,
                    start = Offset(0f, size.width),
                    end = Offset(size.width, size.width),
                    strokeWidth = LINE_STROKE_WIDTH_FACTOR * density
                )
            }
    )
}

// Extension functions
private fun isLastRow(rowIndex: Int, boardSize: Int) = rowIndex == boardSize
private fun isLastColumn(columnIndex: Int, boardSize: Int) = columnIndex == boardSize
private fun isFirstRow(rowIndex: Int) = rowIndex == FIRST_INDEX
private fun isFirstColumn(columnIndex: Int) = columnIndex == FIRST_INDEX
private fun isCorner(rowIndex: Int, columnIndex: Int, boardSize: Int) =
    (isFirstRow(rowIndex) && isFirstColumn(columnIndex)) ||
            (isFirstRow(rowIndex) && isLastColumn(columnIndex, boardSize)) ||
            (isLastRow(rowIndex, boardSize) && isFirstColumn(columnIndex)) ||
            (isLastRow(rowIndex, boardSize) && isLastColumn(columnIndex, boardSize))

@Composable
@Preview
private fun BoardViewPreview() {
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
    GomokuTheme {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
            ) {
                BoardView(
                    Modifier.shimmerEffect(),
                    board = Board(
                        moves = moves,
                        turn = BoardTurn(Player.W, Timer(0, 10)),
                        size = BoardSize.FIFTEEN
                    ),
                    localPlayer = Player.W,
                    onCellClick = {},
                    maxWidth = 300.dp
                )
            }
        }
    }
}