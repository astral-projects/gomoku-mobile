package gomoku.leaderboard.ui.components

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
import gomoku.leaderboard.domain.Leaderboard
import gomoku.leaderboard.domain.RankingInfo
import gomoku.shared.components.ContentNotFound

// Config
private val leaderboardPaddingTop = 10.dp
private val spaceBetweenRows = 5.dp
private const val PROGRESS_INDICATOR_TRACK_COLOR_OPACITY = 0.5f

@Composable
fun LeaderboardTable(
    playersRankingInfo: List<RankingInfo>,
    listState: LazyListState = rememberLazyListState(),
    loading: Boolean = false
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
                        RankingInfoTile(rankData = it)
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
fun LeaderBoardPreviewWithFewPeople() {
    LeaderboardTable(
        playersRankingInfo = Leaderboard.generateRankingInfo(4)
    )
}

@Composable
@Preview
fun LeaderBoardPreviewWithMorePeople() {
    LeaderboardTable(
        playersRankingInfo = Leaderboard.generateRankingInfo(20)
    )
}

@Composable
@Preview
fun LeaderBoardPreviewLoading() {
    LeaderboardTable(
        playersRankingInfo = Leaderboard.generateRankingInfo(20),
        loading = true
    )
}
