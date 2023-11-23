package gomoku.leaderboard.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.LoadState
import gomoku.Loading
import gomoku.getOrNull
import gomoku.leaderboard.domain.Leaderboard
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.leaderboard.domain.RankingInfo
import gomoku.leaderboard.user.domain.UserStats
import pdm.gomoku.R


/**
 * Represents the Leaderboard screen main composable.
 * @param rankingInfo the logged in player full ranking info.
 * @param getItemsFromPage callback to get the items to be displayed in the leaderboard, given a page number.
 * @param onSearchRequest callback to be executed when a search is requested.
 * @param toFindGameScreen callback to be executed when the user clicks on the respective navigation item.
 * @param toAboutScreen callback to be executed when the user clicks on the respective navigation item.
 * @param onLogoutRequest callback to be executed when the user clicks on the respective navigation item.
 */
@Composable
fun LeaderboardScreen(
    listLeaderboard: LoadState<List<UserStats>?>,
    toFindGameScreen: () -> Unit,
    toAboutScreen: () -> Unit,
    onLogoutRequest: () -> Unit,
) {
    val list = listLeaderboard.getOrNull()?.map { it.toRankingInfo() } ?: emptyList<RankingInfo>()

    LeaderboardView(
        searchingLeaderboard = listLeaderboard is Loading,
        rankingInfo = list.firstOrNull() ?: RankingInfo(
            PlayerInfo("dasdas", R.drawable.man),
            1,
            0,
            0,
            0,
            0,
            0
        ),
        getItemsFromPage = { page ->
            Leaderboard.paginatedRankingInfo(
                list = list,
                page = page
            )
        },
        onSearchRequest = { term ->
            list.filter {
                it.playerInfo.name.contains(
                    term.value,
                    ignoreCase = true
                )
            }
        },
        toFindGameScreen = toFindGameScreen,
        toAboutScreen = toAboutScreen,
        onLogoutRequest = onLogoutRequest,
    )
}


@Composable
@Preview(showBackground = true)
private fun LeaderboardScreenPreview() {
    /*
    val nPlayers = 200
    val playersRankingInfo = Leaderboard.generateRankingInfo(nPlayers)
    LeaderboardScreen(
        rankingInfo = playersRankingInfo.first(),
        getItemsFromPage = { page ->
            Leaderboard.paginatedRankingInfo(
                list = playersRankingInfo,
                page = page
            )
        },
        onSearchRequest = { term ->
            playersRankingInfo.filter {
                it.playerInfo.name.contains(
                    term.value,
                    ignoreCase = true
                )
            }
        },
        toFindGameScreen = {},
        toAboutScreen = {},
        onLogoutRequest = {}
    )*/
}