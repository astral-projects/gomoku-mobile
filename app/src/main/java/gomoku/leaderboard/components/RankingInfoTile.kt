package gomoku.leaderboard.components

import androidx.compose.runtime.Composable
import gomoku.ui.components.CustomInfoTile
import gomoku.ui.containers.RankingInfo
import pdm.gomoku.R

@Composable
fun RankingInfoTile(
    rankData: RankingInfo,
) {
    CustomInfoTile(
        leadingIconId = when (rankData.rank) {
            1 -> R.drawable.gold_medal
            2 -> R.drawable.silver_medal
            3 -> R.drawable.bronze_medal
            else -> null
        },
        leadingNumber = rankData.rank,
        labelIconId = rankData.playerInfo.iconId,
        label = rankData.playerInfo.name,
        trailingNumber = rankData.points,
        trailingIconId = R.drawable.coins,
    )
}

/**
 * Auxiliary function to get a random person id
 */
private fun retrieveRandomPerson(): Int {
    // List of all the person ids
    val personIds: List<Int> = listOf(
        R.drawable.man,
        R.drawable.man2,
        R.drawable.man3,
        R.drawable.man4,
        R.drawable.man5,
        R.drawable.woman,
        R.drawable.woman2,
        R.drawable.woman3,
        R.drawable.woman4,
        R.drawable.woman5,
        R.drawable.woman6,
    )
    return personIds.random()
}