package gomoku.ui

import gomoku.domain.IOState
import gomoku.domain.Idle
import gomoku.domain.Loaded
import gomoku.domain.Loading
import org.junit.Assert

/**
 * Base class for view model tests.
 */
abstract class AbstractViewModelTests {
    fun <T> verifyIOStateSequence(collectedStates: List<IOState<T>>) {
        val receivedStates = "Received states: $collectedStates"
        Assert.assertTrue(
            "First state should be Idle. $receivedStates",
            collectedStates.first() is Idle
        )
        Assert.assertTrue(
            "Second state should be Loading. $receivedStates",
            collectedStates[1] is Loading
        )
        Assert.assertTrue(
            "Third state should be Loaded. $receivedStates",
            collectedStates.last() is Loaded
        )
    }
}