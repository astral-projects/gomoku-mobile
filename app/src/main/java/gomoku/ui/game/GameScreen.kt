package gomoku.ui.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import gomoku.domain.LoadState
import gomoku.domain.Loaded
import gomoku.domain.game.Game
import gomoku.domain.game.Timer
import gomoku.domain.game.board.Board
import gomoku.domain.game.board.BoardSize
import gomoku.domain.game.board.BoardTurn
import gomoku.domain.game.moves.Move
import gomoku.domain.game.moves.move.Piece
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square
import gomoku.domain.getOrNull
import gomoku.domain.home.Home.gameName
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.ui.game.components.GameView
import gomoku.ui.shared.background.Background
import gomoku.ui.shared.background.BackgroundConfig
import gomoku.ui.shared.components.HeaderText
import gomoku.ui.shared.theme.GomokuTheme
import pdm.gomoku.R

/**
 * Represents the game screen main composable.
 * @param isDarkTheme the [Boolean] that represents if the theme is dark or not.
 * @param backgroundConfig the [BackgroundConfig] to be used.
 * @param localPlayer the [Player] that is playing locally.
 * @param onLeaveGameRequest the callback to be called when the dismiss button is clicked.
 * @param onCellClick the callback to be called when a cell is clicked.
 * @param gameState the [LoadState] of the [Game] to be displayed.
 */
@Composable
fun GameScreen(
    isDarkTheme: Boolean?,
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    localPlayer: PlayerInfo,
    onLeaveGameRequest: () -> Unit,
    onCellClick: (toSquare: Square) -> Unit,
    gameState: LoadState<Game?>,
) {
    GomokuTheme(darkTheme = isDarkTheme ?: false) {
        Background(
            config = backgroundConfig,
            useBodySurface = false,
            header = {
                HeaderText(text = stringResource(id = gameName))
            },
        ) {

            val g = gameState.getOrNull() ?: Game(
                "I got nothing",
                "https://www.example.com",
                "FREESTYLE",
                Board(emptyMap(), BoardTurn(Player.W, Timer(0, 0)), BoardSize.NINETEEN),
                "DSSDSDDS",
                "WEQEWQWEWQE",
                PlayerInfo("Player W", R.drawable.man5),
                PlayerInfo("Player B", R.drawable.woman2)
            )
            GameView(
                playerInfo = localPlayer,
                localPlayer = Player.W,
                onLeaveGameRequest = onLeaveGameRequest,
                onCellClick = onCellClick,
                game = g,
                isLoading = gameState.toGameStateScreen()
                    .isLoading() || gameState.toGameStateScreen().isIdle(),
            )
        }
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
        isDarkTheme = false,
        backgroundConfig = BackgroundConfig(LocalConfiguration.current),
        localPlayer = PlayerInfo("Player W", R.drawable.man5),
        onLeaveGameRequest = {},
        onCellClick = {},
        gameState = Loaded(
            Result.success(
                Game(
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
        ),

        )
}

