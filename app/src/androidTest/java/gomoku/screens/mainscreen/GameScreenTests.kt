package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import gomoku.game.domain.Game
import gomoku.game.domain.Timer
import gomoku.game.domain.board.Board
import gomoku.game.domain.board.BoardSize
import gomoku.game.domain.board.BoardTurn
import gomoku.game.domain.moves.Move
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import gomoku.game.domain.moves.move.Square
import gomoku.game.ui.GameScreen
import gomoku.leaderboard.domain.PlayerInfo
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import pdm.gomoku.R

class GameScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun click_on_leave_game_navigation_calls_onLeave(){
        // Arrange
        var leaveGameRequested = false
        val moves = mapOf(
            Move(Square(1, 'a'), Piece(Player.W)),
            Move(Square(4, 'b'), Piece(Player.B)),
            Move(Square(3, 'c'), Piece(Player.W)),
            Move(Square(7, 'd'), Piece(Player.B)),
            Move(Square(5, 'e'), Piece(Player.W)),
            Move(Square(3, 'f'), Piece(Player.B)),
        )
        val turn = BoardTurn(
            player = Player.W,
            timer = Timer(0, 55)
        )
        val board = Board(
            moves = moves,
            turn = turn,
            size = BoardSize.NINETEEN
        )
        composeTestRule.setContent {
            GameScreen(
                localPlayer = Player.W,
                onBurgerMenuClick = {},
                onLeaveGameRequest = {
                    leaveGameRequested = true
                },
                onCellClick = {},
                whitePlayer = PlayerInfo("Geralt of Rivia", R.drawable.man),
                blackPlayer = PlayerInfo("Arthur Morgan".repeat(100), R.drawable.man5),
                board = board
            )
        }

        // Act
        composeTestRule.onNodeWithTag(Game.DISMISS_BUTTON_TEXT).performClick()
        assertTrue(leaveGameRequested)
    }

}