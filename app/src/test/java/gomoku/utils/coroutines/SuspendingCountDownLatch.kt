package gomoku.utils.coroutines

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

/**
 * Coroutine synchronizer with the same semantics as
 * java.util.concurrent.CountDownLatch
 *
 * TODO: Should add timeout to [await]
 */
class SuspendingCountDownLatch(initialCount: Int = 1) {

    private val guard = Mutex()
    private var count = initialCount
    private val waiting = mutableListOf<Continuation<Unit>>()

    suspend fun currentCount() = guard.withLock { count }

    suspend fun await() {
        try {
            guard.lock()
            if (count != 0) {
                suspendCancellableCoroutine {
                    waiting.add(it)
                    guard.unlock()
                }
            }
        } finally {
            if (guard.isLocked)
                guard.unlock()
        }
    }

    suspend fun countDown() {
        try {
            guard.lock()
            check(count-- != 0)
            if (count == 0) {
                guard.unlock()
                waiting.forEach { it.resume(Unit) }
            }
        } finally {
            if (guard.isLocked)
                guard.unlock()
        }
    }
}