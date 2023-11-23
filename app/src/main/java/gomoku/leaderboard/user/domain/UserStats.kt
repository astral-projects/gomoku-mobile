package gomoku.leaderboard.user.domain

import gomoku.leaderboard.domain.PlayerInfo
import gomoku.leaderboard.domain.RankingInfo
import pdm.gomoku.R

class UserStats(
    val id: Int,
    val username: String,
    val points: Int,
    val rank: Int,
    val gamesPlayed: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int
) {
    fun toRankingInfo(): RankingInfo {
        return RankingInfo(
            playerInfo = PlayerInfo(username, R.drawable.man),
            rank = rank,
            points = points,
            playedGames = gamesPlayed,
            wins = wins,
            draws = draws,
            losses = losses
        )
    }
}