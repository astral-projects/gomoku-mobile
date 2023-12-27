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

    /**
     * Verifies that the sequence of [IOState]s is [Idle] -> [Loading] -> [Loaded].
     * @param states the sequence of states to verify.
     * @throws AssertionError if the sequence of states does not match the expected sequence.
     */
    fun <T> verifyDefaultIOStateSequence(states: List<IOState<T>>) {
        val receivedStates = "Received states: $states"
        Assert.assertTrue(
            "First state should be Idle. $receivedStates",
            states.first() is Idle
        )
        Assert.assertTrue(
            "Second state should be Loading. $receivedStates",
            states[1] is Loading
        )
        Assert.assertTrue(
            "Third state should be Loaded. $receivedStates",
            states.last() is Loaded
        )
    }
}