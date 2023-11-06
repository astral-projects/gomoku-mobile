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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import gomoku.game.domain.Game
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
import gomoku.home.domain.Home.GAME_NAME
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.ui.background.Background
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.DismissButton
import gomoku.ui.components.TopNavHeader
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R



/**
 * Represents the game screen main composable.
 * @param backgroundConfig the [BackgroundConfig] to be used.
 * @param localPlayer the [Player] that is playing locally.
 * @param onBurgerMenuClick the callback to be called when the burger menu is clicked.
 * @param onLeaveGameRequest the callback to be called when the dismiss button is clicked.
 * @param onCellClick the callback to be called when a cell is clicked.
 * @param whitePlayer the [PlayerInfo] of the white player.
 * @param blackPlayer the [PlayerInfo] of the black player.
 * @param board the [Board] to be displayed.
 */
@Composable
fun GameScreen(
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    localPlayer: Player,
    onBurgerMenuClick: () -> Unit,
    onLeaveGameRequest: () -> Unit,
    onCellClick: (toSquare: Square) -> Unit,
    whitePlayer: PlayerInfo,
    blackPlayer: PlayerInfo,
    board: Board
) {
    GomokuTheme {
        Background(
            config = backgroundConfig,
            header = { TopNavHeader(title = GAME_NAME, onBurgerMenuClick = onBurgerMenuClick) },
            useBodySurface = false,
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
                        label = "${board.turn.timer}"
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    PlayerInfoChip(
                        playerInfo = whitePlayer,
                        trailingIconId = R.drawable.white_circle,
                        select = board.turn.player == Player.W
                    )
                }
                BoardContainer(
                    board = board,
                    localPlayer = localPlayer,
                    onCellClick = onCellClick
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PlayerInfoChip(
                        playerInfo = blackPlayer,
                        trailingIconId = R.drawable.black_circle,
                        select = board.turn.player == Player.B
                    )
                    GameInfoChip(
                        leadingIconId = R.drawable.directions,
                        label = "${board.moves.size}"
                    )
                }
                // Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DismissButton(
                        onButtonText = Game.DISMISS_BUTTON_TEXT,
                        enable = true,
                        onDismiss = onLeaveGameRequest
                    )
                }
            }
        }
    }
}

@Composable
private fun BoardContainer(
    board: Board,
    localPlayer: Player,
    onCellClick: (toSquare: Square) -> Unit
) {
    var actualWidth by remember { mutableIntStateOf(0) }
    Row(
        modifier = Modifier
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

@Preview
@Composable
private fun GameScreenPreview() {
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
    GameScreen(
        backgroundConfig = BackgroundConfig(LocalConfiguration.current),
        localPlayer = Player.W,
        onBurgerMenuClick = {},
        onLeaveGameRequest = {},
        onCellClick = {},
        whitePlayer = PlayerInfo("Geralt of Rivia", R.drawable.man),
        blackPlayer = PlayerInfo("Arthur Morgan".repeat(100), R.drawable.man5),
        board = board
    )
}