package gomoku.ui.leaderboard.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.domain.leaderboard.NumberFormatter
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.domain.leaderboard.RankingInfo
import gomoku.ui.shared.components.CustomInfoTile
import pdm.gomoku.R

/**
 * A [CustomInfoTile] that displays the ranking information of a player.
 * @param rankData The ranking information of a player.
 * @param onClick The callback to be called when the tile is clicked.
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
        trailingLabel = NumberFormatter.format(rankData.points.toString()),
        trailingIconId = R.drawable.coins,
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
fun RankingInfoTilePreview() {
    RankingInfoTile(
        rankData = RankingInfo(
            id = 1,
            rank = 1,
            playerInfo = PlayerInfo(6, "Player 1", R.drawable.man),
            playedGames = 115,
            points = 1000,
            wins = 100,
            losses = 10,
            draws = 5
        ),
        onClick = {}
    )
}