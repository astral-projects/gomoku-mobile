package gomoku.domain

/**
 * Sum type that represents the state of a load operation.
 */
sealed class LoadState<out T>

/**
 * The idle state, i.e. the state before the load operation is started.
 */
object Idle : LoadState<Nothing>()

/**
 * The fail state, i.e. the state if the operation go .
 */
object Fail : LoadState<Nothing>()

/**
 * The loading state, i.e. the state while the load operation is in progress.
 */
object Loading : LoadState<Nothing>()


/**
 * The saving state, i.e. the state while the save operation is in progress.
 */
object Saving : LoadState<Nothing>()

/**
 * The loaded state, i.e. the state after the load operation has finished.
 * @param value the result of the load operation.
 */
data class Loaded<T>(val value: Result<T>) : LoadState<T>()

/**
 * The saved state, i.e. the state after the save operation has finished.
 * @param value the result of the save operation. If the save operation
 * was successful, the result is the saved value. If the save operation
 * failed, the result is the exception that caused the failure.
 */
data class Saved<T>(val value: Result<T>) : LoadState<T>()

/**
 * Returns a new [LoadState] in the idle state.
 */
fun idle(): Idle = Idle

/**
 * Returns a new [LoadState] in the loading state.
 */
fun loading(): Loading = Loading

/**
 * Returns a new [LoadState] in the fail state.
 */
fun fail(): Fail = Fail

/**
 * Returns a new [LoadState] in the loaded state.
 */
fun <T> loaded(value: Result<T>): Loaded<T> = Loaded(value)

/**
 * Returns a new [LoadState] in the saved state.
 */
fun <T> saved(value: Result<T>): Saved<T> = Saved(value)

/**
 * Returns a new [LoadState] in the saving state.
 */
fun saving(): Saving = Saving

/**
 * Returns a new [LoadState] in the loaded state with a successful result.
 */
fun <T> success(value: T): Loaded<T> = loaded(Result.success(value))

/**
 * Returns a new [LoadState] in the loaded state with a failed result.
 */
fun <T> failure(error: Throwable): Loaded<T> = loaded(Result.failure(error))

/**
 * Returns a new [LoadState] in the saved state with a successful result.
 */
fun <T> saveSuccess(value: T): Saved<T> = saved(Result.success(value))

/**
 * Returns a new [LoadState] in the saved state with a failed result.
 */
fun <T> saveFailure(error: Throwable): Saved<T> = saved(Result.failure(error))

/**
 * Returns the result of the load operation, if one is available.
 */
fun <T> LoadState<T>.getOrNull(): T? = when (this) {
    is Loaded -> value.getOrNull()
    else -> null
}

/**
 * Returns the result of the load operation, if one is available, or throws
 * the exception that caused the load operation to fail. If the load operation
 * is still in progress, an [IllegalStateException] is thrown.
 */
@Throws(IllegalStateException::class)
fun <T> LoadState<T>.getOrThrow(): T = when (this) {
    is Loaded -> value.getOrThrow()
    else -> throw IllegalStateException("No value available")
}

/**
 * Returns the exception that caused the load operation to fail, if one is available.
 */
fun <T> LoadState<T>.exceptionOrNull(): Throwable? = when (this) {
    is Loaded -> value.exceptionOrNull()
    else -> null
}