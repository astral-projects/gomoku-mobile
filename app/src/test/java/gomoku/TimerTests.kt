package gomoku

import gomoku.domain.game.Timer
import org.junit.Test

class TimerTests {

    @Test(expected = IllegalStateException::class)
    fun `start on a started stopWatch throws`() {
        // Arrange
        val sut = Timer(3, 14)
        // Act
        // TODO(continue)
    }

}



