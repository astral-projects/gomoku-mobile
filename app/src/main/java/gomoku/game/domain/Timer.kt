package gomoku.game.domain

/**
 * Represents a timer to count down the time of a player's turn.
 */
class Timer(val minutes: Int, private val seconds: Int) {
    init {
        require(minutes >= 0) { "Minutes must be greater than or equal to 0" }
        require(seconds >= 0) { "Seconds must be greater than or equal to 0" }
        require(seconds < 60) { "Seconds must be less than 60" }
    }

    override fun toString(): String {
        val seconds = if (seconds < 10) "0$seconds" else seconds.toString()
        return "$minutes:$seconds"
    }
}
