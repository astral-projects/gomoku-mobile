package gomoku.domain

/**
 * Represents a stop watch value. Instances of this class are immutable.
 * @property minutes the number of minutes
 * @property seconds the number of seconds
 * @property milliseconds the number of milliseconds
 */
data class StopWatchValue(val minutes: Int, val seconds: Int, val milliseconds: Int) {
    init {
        require(minutes >= 0) { "minutes must be non-negative" }
        require(seconds in 0..59) { "seconds must be between 0 and 59" }
        require(milliseconds in 0..999) { "milliseconds must be between 0 and 999" }
    }
}