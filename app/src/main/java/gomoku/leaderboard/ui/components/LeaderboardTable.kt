package gomoku.leaderboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.leaderboard.domain.Leaderboard
import gomoku.leaderboard.domain.RankingInfo
import gomoku.ui.components.ContentNotFound

// Config
private val leaderboardPaddingTop = 10.dp
private val spaceBetweenRows = 5.dp

@Composable
fun LeaderboardTable(
    playersRankingInfo: List<RankingInfo>,
    listState: LazyListState = rememberLazyListState()
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
                    ContentNotFound(text = "No players found that match your search query.")
                }
            } else {
                playersRankingInfo.forEach {
                    item {
                        RankingInfoTile(rankData = it)
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
        playersRankingInfo = Leaderboard.generateFakeRankingInfo(4)
    )
}

@Composable
@Preview
fun LeaderBoardPreviewWithMorePeople() {
    LeaderboardTable(
        playersRankingInfo = Leaderboard.generateFakeRankingInfo(20)
    )
}
