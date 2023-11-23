package gomoku.game.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import gomoku.LoadState
import gomoku.Loaded
import gomoku.game.domain.Game
import gomoku.game.domain.Timer
import gomoku.game.domain.board.Board
import gomoku.game.domain.board.BoardSize
import gomoku.game.domain.board.BoardTurn
import gomoku.game.domain.moves.Move
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import gomoku.game.domain.moves.move.Square
import gomoku.getOrNull
import gomoku.home.domain.Home.gameName
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.shared.background.Background
import gomoku.shared.background.BackgroundConfig
import gomoku.shared.components.HeaderText
import gomoku.shared.theme.GomokuTheme
import pdm.gomoku.R

/**
 * Represents the game screen main composable.
 * @param backgroundConfig the [BackgroundConfig] to be used.
 * @param localPlayer the [Player] that is playing locally.
 * @param onLeaveGameRequest the callback to be called when the dismiss button is clicked.
 * @param onCellClick the callback to be called when a cell is clicked.
 * @param gameState the [LoadState] of the [Game] to be displayed.
 */
@Composable
fun GameScreen(
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    localPlayer: PlayerInfo,
    onLeaveGameRequest: () -> Unit,
    onCellClick: (toSquare: Square) -> Unit,
    gameState: LoadState<Game?>,
) {
    GomokuTheme {
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
            //TODO(BACAREUFL WITH LLOGCU LocalPlayer)
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


//TODO(The Preview is not working because of the launch effect)
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

