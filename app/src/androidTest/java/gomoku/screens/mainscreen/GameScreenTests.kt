package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import gomoku.domain.Loaded
import gomoku.domain.game.Game
import gomoku.domain.game.Timer
import gomoku.domain.game.board.Board
import gomoku.domain.game.board.BoardSize
import gomoku.domain.game.board.BoardTurn
import gomoku.domain.game.moves.move.Player
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.ui.game.GameScreen
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import pdm.gomoku.R

class GameScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun click_on_leave_game_navigation_calls_onLeave() {
        // Arrange
        var leaveGameRequested = false
        composeTestRule.setContent {
            GameScreen(
                isDarkTheme = false,
                localPlayer = PlayerInfo("Player W", R.drawable.man5),
                onLeaveGameRequest = {
                    leaveGameRequested = true
                },
                onCellClick = {},
                gameState = Loaded(
                    Result.success(
                        Game(
                            "I got nothing",
                            "https://www.example.com",
                            "FREESTYLE",
                            Board(emptyMap(), BoardTurn(Player.W, Timer(0, 0)), BoardSize.NINETEEN),
                            "DSSDSDDS",
                            "WEQEWQWEWQE",
                            PlayerInfo("Player W", R.drawable.man5),
                            PlayerInfo("Player B", R.drawable.woman2)
                        )
                    )
                )
            )
        }

        // Act
        composeTestRule.onNodeWithTag("Leave game").performClick()
        assertTrue(leaveGameRequested)
    }

}