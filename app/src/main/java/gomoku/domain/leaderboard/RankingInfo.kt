package gomoku.domain.leaderboard

/**
 * Represents a player's ranking information.
 */
data class RankingInfo(
    val id: Int,
    val playerInfo: PlayerInfo,
    val rank: Int,
    val points: Int,
    val wins: Int,
    val losses: Int,
    val draws: Int,
    val playedGames: Int
) {
    constructor(
        id: Int,
        playerInfo: PlayerInfo
    ) : this(
        id = id,
        playerInfo = playerInfo,
        rank = 0,
        points = 0,
        wins = 0,
        losses = 0,
        draws = 0,
        playedGames = 0
    )
}