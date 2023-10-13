import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import gomoku.domain.board.Board
import gomoku.domain.Timer
import gomoku.domain.Turn
import gomoku.domain.moves.Move
import gomoku.domain.moves.move.Piece
import gomoku.domain.moves.move.Player
import gomoku.domain.moves.move.Square
import gomoku.ui.GAME_NAME
import gomoku.ui.components.generic.TopNavHeader
import gomoku.ui.background.Background
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.generic.CustomIconChip
import gomoku.ui.components.generic.DismissButton
import gomoku.ui.containers.PlayerInfo
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R

@Composable
fun GameScreen(
    backgroundConfig: BackgroundConfig,
    onBurgerMenuClick: () -> Unit,
    onLeaveGameRequest: () -> Unit,
    whitePlayer: PlayerInfo,
    blackPlayer: PlayerInfo,
    board: Board
) {
    GomokuTheme {
        Background(
            config = backgroundConfig,
            header = {
                TopNavHeader(title = GAME_NAME, onBurgerMenuClick = onBurgerMenuClick)
            },
            useBodySurface = false,
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween
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
                        playerInfo = blackPlayer,
                        trailingIconId = R.drawable.white_circle,
                        select = board.turn.player == Player.w
                    )
                }
                BoardView(board = board)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PlayerInfoChip(
                        playerInfo = blackPlayer,
                        trailingIconId = R.drawable.black_circle,
                        select = board.turn.player == Player.b
                    )
                    GameInfoChip(
                        leadingIconId = R.drawable.directions,
                        label = "${board.moves.size}"
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DismissButton(
                        onButtonText = "Leave Game",
                        onDismiss = onLeaveGameRequest
                    )
                }
            }
        }
    }
}

@Composable
private fun GameInfoChip(
    leadingIconId: Int,
    label: String
) {
    CustomIconChip(
        leadingIconId = leadingIconId,
        label = label,
        textColor = Color.White,
        useSecondaryColor = true
    )
}

@Composable
private fun PlayerInfoChip(
    playerInfo: PlayerInfo,
    trailingIconId: Int,
    select: Boolean
) {
    CustomIconChip(
        label = playerInfo.name,
        select = select,
        leadingIconId = playerInfo.iconId,
        trailingIconId = trailingIconId
    )
}

@Composable
fun BoardView(board: Board) {
    // TODO: Implement board view
}

@Preview
@Composable
fun GameScreenPreview() {
    val turn = Turn(
        player = Player.w,
        timer = Timer(0, 55)
    )
    val moves = mapOf(
        Move(Square(3, 6), Piece(Player.w)),
        Move(Square(4, 4), Piece(Player.b)),
        Move(Square(3, 2), Piece(Player.w)),
        Move(Square(7, 3), Piece(Player.b)),
        Move(Square(5, 4), Piece(Player.w)),
        Move(Square(3, 5), Piece(Player.b)),
    )
    val board = Board(
        moves = moves,
        turn = turn
    )
    GameScreen(
        backgroundConfig = BackgroundConfig(LocalConfiguration.current),
        onBurgerMenuClick = {},
        onLeaveGameRequest = {},
        whitePlayer = PlayerInfo("Geralt of Rivia", R.drawable.man),
        blackPlayer = PlayerInfo("Arthur Morgan", R.drawable.man5),
        board = board
    )
}