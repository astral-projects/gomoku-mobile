package gomoku.utils.coroutines

/**
 * A gate synchronizer.
 * Await suspends calling coroutines until the gate is opened through a call
 * to [open].
 *
 * TODO: Should add timeout to [await]
 */
class SuspendingGate {
    private val latch = SuspendingCountDownLatch(initialCount = 1)

    suspend fun open() {
        if (latch.currentCount() != 0)
            latch.countDown()
    }

    suspend fun await() = latch.await()
}