package gomoku.leaderboard.domain

import pdm.gomoku.R

/**
 * Represents data and functionality related to the leaderboard screen.
 */
object Leaderboard {

    val searchBarPlaceHolder = R.string.leaderboard_search_bar_hint
    val title = R.string.leaderboard_title
    val noResultsFound = R.string.leaderboard_no_results_found
    const val MAX_POINTS_VALUE = 99999
    private const val PAGE_SIZE = 20

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
     * @param nPlayers The number of players to generate.
     * @return A list of [RankingInfo].
     */
    fun generateRankingInfo(nPlayers: Int): List<RankingInfo> {
        return (1..nPlayers).fold(listOf()) { rankingInfo, rank ->
            val player = fakePlayers[(rank - 1) % fakePlayers.size]
            val points = (nPlayers - rank + 1) * 100
            rankingInfo + RankingInfo(player, rank, points)
        }
    }

    /**
     * Returns a sublist of [list] with [PAGE_SIZE] elements starting at [page] number.
     * @param list The list to be paginated.
     * @param page The page number to retrieve.
     * @return A sublist of [list].
     */
    fun paginatedRankingInfo(list: List<RankingInfo>, page: Int): List<RankingInfo> {
        val start = (page - 1) * PAGE_SIZE
        val end = start + PAGE_SIZE
        val actualEnd = end.coerceAtMost(list.size)
        return list.subList(start.coerceAtMost(actualEnd), actualEnd)
    }
}
