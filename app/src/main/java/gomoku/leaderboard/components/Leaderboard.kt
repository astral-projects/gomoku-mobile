package gomoku.leaderboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.containers.PlayerInfo
import gomoku.ui.containers.RankingInfo
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R

@Composable
fun Leaderboard(personsRankingInfo: List<RankingInfo>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    ) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(bottom = 2.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            personsRankingInfo.forEach {
                item {
                        RankingInfoTile(rankData = it)
                }
            }
        }
    }
}

@Composable
@Preview
fun LeaderBoardPreviewWithFewPeople() {
    GomokuTheme {
        Leaderboard(
            listOf(
                RankingInfo(PlayerInfo("Player 1asdasdadsasd", R.drawable.man), 1, 1000),
                RankingInfo(PlayerInfo("Player 2", R.drawable.man2), 2, 900),
                RankingInfo(PlayerInfo("Player 3", R.drawable.woman), 3, 800),
                RankingInfo(PlayerInfo("Player 4", R.drawable.woman2), 4, 700),
            )
        )
    }
}

@Composable
@Preview
fun LeaderBoardPreviewWithMorePeople() {
    Leaderboard(
        listOf(
            RankingInfo(PlayerInfo("Player 1", R.drawable.man), 1, 1000),
            RankingInfo(PlayerInfo("Player 2", R.drawable.man2), 2, 900),
            RankingInfo(PlayerInfo("Player 3", R.drawable.woman), 3, 800),
            RankingInfo(PlayerInfo("Player 4", R.drawable.woman2), 4, 700),
            RankingInfo(PlayerInfo("Player 5", R.drawable.woman3), 5, 600),
            RankingInfo(PlayerInfo("Player 6", R.drawable.man3), 6, 500),
            RankingInfo(PlayerInfo("Player 7", R.drawable.man4), 7, 400),
            RankingInfo(PlayerInfo("Player 8", R.drawable.woman4), 8, 300),
            RankingInfo(PlayerInfo("Player 9", R.drawable.woman5), 9, 200),
        )
    )
}
