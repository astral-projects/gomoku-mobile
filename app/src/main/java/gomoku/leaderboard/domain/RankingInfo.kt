package gomoku.leaderboard.domain

/**
 * Represents a player's ranking information.
 */
data class RankingInfo (
    val playerInfo: PlayerInfo,
    val rank: Int,
    val points: Int
)