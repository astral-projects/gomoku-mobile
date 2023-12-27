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
 * Subscribes to the flow before calling the operation, while collecting all the values emitted by the flow within the specified [millisToCollect] timeout.
 * If the flow does not emit any value, the list of collected values will be empty.
 * @param millisToDelayOpCall the number of milliseconds to delay before calling the operation.
 * @param millisToCollect the number of milliseconds to wait for the values to be collected.
 * @param operation the operation that will be called after the flow is subscribed and after the [millisToDelayOpCall] delay. This operation should call the method that will emit values in the flow.
 * @return the list of values emitted by the flow.
 */
suspend fun <T> Flow<T>.subscribeBeforeCallingOperation(
    millisToDelayOpCall: Long = 1000,
    millisToCollect: Long = 3000,
    operation: () -> Unit,
): List<T> = coroutineScope {
    val gate = SuspendingGate()
    lateinit var collectedValues: List<T>
    val collectJob = launch {
        collectedValues =
            this@subscribeBeforeCallingOperation.collectAllWithTimeout(millisToCollect)
        gate.open()
    }

    // if the operation is not delayed,
    // the flow will not have time to collect the first values
    delay(millisToDelayOpCall)

    // operation call
    operation()

    // wait for the flow to collect the values
    gate.await()

    // cancel the collect job
    collectJob.cancelAndJoin()

    collectedValues
}
