package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import gomoku.domain.game.Timer
import gomoku.domain.game.board.Board
import gomoku.domain.game.board.BoardSize
import gomoku.domain.game.board.BoardTurn
import gomoku.domain.game.match.Game
import gomoku.domain.game.match.GameState
import gomoku.domain.game.moves.move.Player
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.ui.game.GameScreen
import gomoku.ui.game.GameScreenState
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import pdm.gomoku.R

class GameScreenTests {
    private val leaveGameDialog = "Yes"

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun click_on_leave_game_navigation_calls_onLeave() {

        // Arrange
        var leaveGameRequested = false
        composeTestRule.setContent {
            GameScreen(
                isDarkTheme = false,
                onLeaveGameRequest = {
                    leaveGameRequested = true
                },
                onCellClick = {},
                gameState = GameScreenState.GameLoadedAndYourTurn(
                    Game(
                        1,
                        variantId = 1,
                        state = GameState.IN_PROGRESS,
                        board = Board(
                            emptyMap(),
                            BoardTurn(Player.W, Timer(0, 0)),
                            winner = null,
                            size = BoardSize.NINETEEN
                        ),
                        host = PlayerInfo(2, "joao", R.drawable.man5),
                        guest = PlayerInfo(3, "luis", R.drawable.woman2),
                        localPlayer = Player.W,
                    )
                ),
                onGameEnd = {},
            )
        }

        // Act
        composeTestRule.onNodeWithTag("Leave game").performClick()
        composeTestRule.onNodeWithTag("Yes").performClick()
        assertTrue(leaveGameRequested)
    }
}
