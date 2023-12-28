package gomoku.ui.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import gomoku.domain.IOState
import gomoku.domain.Loaded
import gomoku.domain.game.Timer
import gomoku.domain.game.board.Board
import gomoku.domain.game.board.BoardSize
import gomoku.domain.game.board.BoardTurn
import gomoku.domain.game.match.Game
import gomoku.domain.game.moves.Move
import gomoku.domain.game.moves.move.Piece
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square
import gomoku.domain.getOrNull
import gomoku.domain.home.Home.gameName
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.domain.variant.OpeningRule
import gomoku.domain.variant.VariantConfig
import gomoku.domain.variant.VariantName
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
 * @param localPlayer the [PlayerInfo] of the local player.
 * @param onLeaveGameRequest the callback to be called when the dismiss button is clicked.
 * @param onCellClick the callback to be called when a cell is clicked.
 * @param gameState the [IOState] of the [Game] to be displayed.
 */
@Composable
fun GameScreen(
    isDarkTheme: Boolean?,
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    localPlayer: PlayerInfo,
    onLeaveGameRequest: () -> Unit,
    onCellClick: (toSquare: Square) -> Unit,
    gameState: IOState<Game>,
) {
    GomokuTheme(darkTheme = isDarkTheme ?: false) {
        Background(
            config = backgroundConfig,
            useBodySurface = false,
            header = { HeaderText(text = stringResource(id = gameName)) },
        ) {
            val game = gameState.getOrNull()
            val gameScreenState = gameState.toGameStateScreen()
            GameView(
                playerInfo = localPlayer,
                localPlayer = Player.W,
                onLeaveGameRequest = onLeaveGameRequest,
                onCellClick = onCellClick,
                game = game ?: Game(
                    id = 1,
                    variant = VariantConfig(
                        id = 1,
                        name = VariantName.FREESTYLE,
                        openingRule = OpeningRule.LONG_PRO,
                        boardSize = BoardSize.NINETEEN
                    ),
                    board = Board(
                        moves = emptyMap(),
                        turn = BoardTurn(
                            player = Player.W,
                            timer = Timer(0, 55)
                        ),
                        size = BoardSize.NINETEEN
                    ),
                    host = PlayerInfo("Player W", R.drawable.man5),
                    guest = PlayerInfo(
                        "Player B", R.drawable.woman2
                    )
                ),
                isLoading = gameScreenState.isLoading() || gameScreenState.isIdle(),
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
                    id = 1,
                    variant = VariantConfig(
                        id = 1,
                        name = VariantName.FREESTYLE,
                        openingRule = OpeningRule.LONG_PRO,
                        boardSize = BoardSize.NINETEEN
                    ),
                    board = board,
                    host = PlayerInfo("Player W", R.drawable.man5),
                    guest = PlayerInfo(
                        "Player B", R.drawable.woman2
                    )
                )
            )
        )
    )
}

