package gomoku.leaderboard.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.leaderboard.domain.Leaderboard
import gomoku.leaderboard.domain.RankingInfo
import gomoku.shared.components.CustomInfoTile
import pdm.gomoku.R

/**
 * A [CustomInfoTile] that displays the ranking information of a player.
 * @param rankData The ranking information of a player.
 */
@Composable
fun RankingInfoTile(
    rankData: RankingInfo,
    onClick: (RankingInfo) -> Unit
) {
    CustomInfoTile(
        data = rankData,
        leadingIconId = when (rankData.rank) {
            1 -> R.drawable.gold_medal
            2 -> R.drawable.silver_medal
            3 -> R.drawable.bronze_medal
            else -> null
        },
        leadingLabel = rankData.rank.toString(),
        labelIconId = rankData.playerInfo.iconId,
        label = rankData.playerInfo.name,
        trailingLabel = if (rankData.points > Leaderboard.MAX_POINTS_VALUE) ">${Leaderboard.MAX_POINTS_VALUE}" else rankData.points.toString(),
        trailingIconId = R.drawable.coins,
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
fun RankingInfoTilePreview() {
    RankingInfoTile(
        rankData = RankingInfo(
            rank = 1,
            playerInfo = Leaderboard.fakePlayers[0],
            playedGames = 115,
            points = 1000,
            wins = 100,
            losses = 10,
            draws = 5

        ),
        onClick = {}
    )
}