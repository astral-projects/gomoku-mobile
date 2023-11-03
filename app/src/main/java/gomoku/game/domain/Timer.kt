package gomoku.game.domain

data class Timer(val minutes: Int, private val seconds: Int) {
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
