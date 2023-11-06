package gomoku.leaderboard.ui.components

import androidx.compose.runtime.Composable
import gomoku.leaderboard.domain.Leaderboard
import gomoku.leaderboard.domain.RankingInfo
import gomoku.ui.components.CustomInfoTile
import pdm.gomoku.R

/**
 * A [CustomInfoTile] that displays the ranking information of a player.
 * @param rankData The ranking information of a player.
 */
@Composable
fun RankingInfoTile(
    rankData: RankingInfo,
) = CustomInfoTile(
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
)