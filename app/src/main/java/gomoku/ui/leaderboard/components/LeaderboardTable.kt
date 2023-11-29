package gomoku.ui.leaderboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.domain.leaderboard.Leaderboard
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.domain.leaderboard.RankingInfo
import gomoku.ui.shared.components.ContentNotFound
import pdm.gomoku.R

// Config
private val leaderboardPaddingTop = 10.dp
private val spaceBetweenRows = 5.dp
private const val PROGRESS_INDICATOR_TRACK_COLOR_OPACITY = 0.5f

@Composable
fun LeaderboardTable(
    playersRankingInfo: List<RankingInfo>,
    listState: LazyListState = rememberLazyListState(),
    loading: Boolean = false,
    onClick: (RankingInfo) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = leaderboardPaddingTop)
    ) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(spaceBetweenRows),
        ) {
            if (playersRankingInfo.isEmpty()) {
                item {
                    ContentNotFound(text = stringResource(Leaderboard.noResultsFound))
                }
            } else {
                playersRankingInfo.forEach {
                    item {
                        RankingInfoTile(rankData = it, onClick = onClick)
                    }
                }
                if (loading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.fillMaxHeight(),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = PROGRESS_INDICATOR_TRACK_COLOR_OPACITY)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun LeaderBoardPreviewWithMorePeople() {
    LeaderboardTable(
        playersRankingInfo = generateRankingInfo(20),
        onClick = {}
    )
}

@Composable
@Preview
fun LeaderBoardPreviewLoading() {
    LeaderboardTable(
        playersRankingInfo = generateRankingInfo(20),
        loading = true,
        onClick = {}
    )
}

private val fakePlayers = listOf(
    PlayerInfo("John Marston", R.drawable.man),
    PlayerInfo("Arthur Morgan", R.drawable.man2)
)

/**
 * Generates a list of [RankingInfo] with fake data. The list will have [nPlayers] elements,
 * and the **ranking** will be **in ascending order** while the **points** will be **in descending order**.
 * @param nPlayers The number of players to generate.
 * @return A list of [RankingInfo].
 */
private fun generateRankingInfo(nPlayers: Int): List<RankingInfo> {
    return (1..nPlayers).fold(listOf()) { acc, i ->
        val playerInfo = fakePlayers.random()
        val rankingInfo = RankingInfo(
            id = i,
            playerInfo = playerInfo,
            rank = i,
            points = nPlayers - i,
            wins = i,
            losses = nPlayers - i,
            draws = i,
            playedGames = nPlayers
        )
        acc + rankingInfo
    }
}