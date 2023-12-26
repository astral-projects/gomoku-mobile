package gomoku.utils

import gomoku.domain.leaderboard.UserStats
import java.util.UUID

/*
 * Centralizes the generation of test data.
 */
object TestDataGenerator {

    /**
     * Generates a random [String] using [UUID.randomUUID] and then truncates it to the given [maxLength].
     * @param maxLength maximum length of the generated string.
     * Defaults to **10**.
     * Must not exceed **36**, since the string is truncated to the length of the UUID.
     *
     */
    fun newTestString(
        minLength: Int = 0,
        maxLength: Int = 10
    ): String = UUID.randomUUID().toString().substring(minLength, maxLength.coerceAtMost(36))

    /**
     * Generates a random positive number.
     */
    fun newTestNumber() = (1..Int.MAX_VALUE).random()

    fun generateUsersStats(n: Int): List<UserStats> {
        val list = mutableListOf<UserStats>()
        repeat(n) {
            val user = UserStats(
                id = newTestNumber().coerceIn(1, Int.MAX_VALUE),
                username = newTestString(),
                points = newTestNumber(),
                rank = newTestNumber(),
                gamesPlayed = newTestNumber(),
                wins = newTestNumber(),
                draws = newTestNumber(),
                losses = newTestNumber(),
                iconId = newTestNumber()
            )
            list.add(user)
        }
        return list
    }

    /**
     * Generates a random number between this [Int] and [end] (inclusive).
     */
    infix fun Int.randomTo(end: Int) = (this..end).random()
}