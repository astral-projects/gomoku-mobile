package gomoku.leaderboard.domain

import pdm.gomoku.R

/**
 * Represents data and functionality related to the leaderboard screen.
 * Provides a way to generate fake ranking info.
 */
object Leaderboard {

    const val MAX_POINTS_VALUE = 99999
    const val SEARCH_BAR_PLACEHOLDER = "Search a player..."
    const val TITLE = "Leaderboard"

    /**
     * A list of fake players.
     */
    val fakePlayers = listOf(
        PlayerInfo("John Marston", R.drawable.man),
        PlayerInfo("Arthur Morgan", R.drawable.man2),
        PlayerInfo("Dutch van der Linde", R.drawable.man3),
        PlayerInfo("Bill Williamson", R.drawable.man4),
        PlayerInfo("Javier Escuella", R.drawable.man5),
        PlayerInfo("Abigail Roberts", R.drawable.woman),
        PlayerInfo("Sadie Adler", R.drawable.woman2),
        PlayerInfo("Karen Jones", R.drawable.woman3),
        PlayerInfo("Mary-Beth Gaskill", R.drawable.woman4),
        PlayerInfo("Tilly Jackson", R.drawable.woman5)
    )

    /**
     * Generates a list of [RankingInfo] with fake data. The list will have [nPlayers] elements,
     * and the **ranking** will be **in ascending order** while the **points** will be **in descending order**.
     * The function will not return repeated players, unless there are more requested players than
     * the current number of fake players in [fakePlayers].
     * @param nPlayers The number of players to generate.
     * @return A list of [RankingInfo].
     */
    fun generateFakeRankingInfo(nPlayers: Int): List<RankingInfo> {
        return (1..nPlayers).fold(listOf()) { rankingInfo, rank ->
            val player = fakePlayers[(rank - 1) % fakePlayers.size]
            val points = (nPlayers - rank + 1) * 1000
            rankingInfo + RankingInfo(player, rank, points)
        }
    }
}
