package gomoku.utils.flows

import gomoku.utils.coroutines.SuspendingGate
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

private const val THREE_SECONDS = 3000L

/**
 * Collects the first value emitted by the flow within the specified timeout.
 * If the flow does not emit any value within the timeout, an exception is thrown.
 * @param timeout the timeout in milliseconds.
 * @return the first value emitted by the flow.
 */
@Throws(IllegalStateException::class)
suspend fun <T> Flow<T>.collectWithTimeout(timeout: Long = THREE_SECONDS): T =
    coroutineScope {
        var collectedValue: T? = null
        try {
            withTimeout(timeout) {
                println("collecting")
                collect {
                    println("collected $it")
                    collectedValue = it
                }
            }
        } catch (_: TimeoutCancellationException) {
            println("timeout")
        }
        collectedValue
            ?: throw IllegalStateException("Flow did not emit any value within the timeout.")
    }

/**
 * Collects all the values emitted by the flow within the specified timeout.
 * If the flow does not emit any value withing the timeout, the list of values will be empty.
 * @param timeout the timeout in milliseconds.
 * @return the list of values emitted by the flow.
 */
suspend fun <T> Flow<T>.collectAllWithTimeout(timeout: Long = THREE_SECONDS): List<T> =
    coroutineScope {
        val collectedValues = mutableListOf<T>()
        try {
            withTimeout(timeout) {
                while (true) {
                    collect {
                        println("collecting $it")
                        collectedValues.add(it)
                    }
                }
            }
        } catch (_: TimeoutCancellationException) {
            println("timeout")
        }
        collectedValues
    }

/**
 * Subscribes to the flow before calling the operation, while collecting all the values emitted by the flow within the specified timeout.
 * If the flow does not emit any value withing the timeout, the list of values will be empty.
 * @param operation the operation to call after subscribing the flow.
 * @return the list of values emitted by the flow.
 */
suspend fun <T> Flow<T>.subscribeBeforeCallingOperation(
    operation: () -> Unit,
): List<T> = coroutineScope {
    val gate = SuspendingGate()
    lateinit var collectedValues: List<T>
    val collectJob = launch {
        collectedValues = this@subscribeBeforeCallingOperation.collectAllWithTimeout()
        gate.open()
    }

    // if the operation is not delayed,
    // the flow will not have time to collect the first values
    delay(1000)

    // operation call
    operation()

    // wait for the flow to collect the values
    gate.await()

    // cancel the collect job
    collectJob.cancelAndJoin()

    collectedValues
}
