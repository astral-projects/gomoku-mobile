package gomoku.domain.leaderboard

import gomoku.domain.login.UserInfo

class UserStats(
    val id: Int,
    val username: String,
    val points: Int,
    val rank: Int,
    val gamesPlayed: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int,
    val iconId: Int
) {
    /**
     * Builds a new user with all stats set to 0.
     */
    constructor(user: UserInfo) : this(
        id = user.id,
        username = user.username,
        points = 0,
        rank = 0,
        gamesPlayed = 0,
        wins = 0,
        draws = 0,
        losses = 0,
        iconId = user.iconId
    )

    fun toRankingInfo(): RankingInfo {
        return RankingInfo(
            id = id,
            playerInfo = PlayerInfo(username, iconId),
            rank = rank,
            points = points,
            playedGames = gamesPlayed,
            wins = wins,
            draws = draws,
            losses = losses
        )
    }
}