package gomoku.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.leaderboard.components.Leaderboard
import gomoku.ui.background.Background
import gomoku.ui.components.ClickableIcon
import gomoku.ui.components.SearchBar
import gomoku.ui.components.TopNavHeader
import gomoku.ui.containers.PlayerInfo
import gomoku.ui.containers.RankingInfo
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R

private val iconPadding = 20.dp
private val bottomIconPadding = 15.dp

@Composable
fun LeaderBoardScreen(
    // TODO("change to Term")
    onSearch: (String) -> Unit,
    onBurgerMenuClick: () -> Unit,
    onTargetClick: () -> Unit,
    onLeaderBoardClick: () -> Unit,
) {
    GomokuTheme {
        Background(
            header = {
                TopNavHeader(title = "Leaderboard", onBurgerMenuClick = onBurgerMenuClick)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    SearchBar(
                        text = "Search a player...",
                        iconId = R.drawable.search,
                        onSearch = onSearch,
                    )
                }
            },
            ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.911f)
                        .align(Alignment.TopCenter)
                ) {
                    Leaderboard(
                        listOf(
                            RankingInfo(PlayerInfo("Player 1", R.drawable.man), 1, 10000),
                            RankingInfo(PlayerInfo("Player 2", R.drawable.man2), 2, 9000),
                            RankingInfo(PlayerInfo("Player 3", R.drawable.woman), 3, 8000),
                            RankingInfo(PlayerInfo("Player 4", R.drawable.woman2), 4, 7000),
                            RankingInfo(PlayerInfo("Player 5", R.drawable.man3), 5, 6000),
                            RankingInfo(PlayerInfo("Player 6", R.drawable.man4), 6, 5000),
                            RankingInfo(PlayerInfo("Player 7", R.drawable.woman3), 7, 4000),
                            RankingInfo(PlayerInfo("Player 8", R.drawable.woman4), 8, 3000),
                            RankingInfo(PlayerInfo("Player 9", R.drawable.man5), 9, 2000),
                            RankingInfo(PlayerInfo("Player 10", R.drawable.man), 10, 1000),
                            RankingInfo(PlayerInfo("Player 11", R.drawable.man), 11, 900),
                            RankingInfo(PlayerInfo("Player 11", R.drawable.man), 11, 900),
                            RankingInfo(PlayerInfo("Player 11", R.drawable.man), 11, 900),
                            RankingInfo(PlayerInfo("Player 11", R.drawable.man), 11, 900),
                            RankingInfo(PlayerInfo("Player 11", R.drawable.man), 11, 900),
                            RankingInfo(PlayerInfo("Player 11", R.drawable.man), 11, 900),
                            RankingInfo(PlayerInfo("Player 11", R.drawable.man), 11, 900),
                            RankingInfo(PlayerInfo("Player 11", R.drawable.man), 11, 900),
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(iconPadding, bottomIconPadding)
                ) {
                    Target(iconId = R.drawable.target, onClick = onTargetClick)
                    LeaderBoardIcon(iconId = R.drawable.leaderboard, onClick = onLeaderBoardClick)
                }
            }



        }
    }
}

@Composable
fun Target(iconId: Int, onClick: () -> Unit) {
    ClickableIcon(iconId = iconId, onClick = onClick)
}

@Composable
fun LeaderBoardIcon(iconId: Int, onClick: () -> Unit) {
    ClickableIcon(iconId = iconId, onClick = onClick)
}

@Composable
@Preview(showBackground = true)
fun LeaderBoardScreenPreview() {
    LeaderBoardScreen(onSearch = {}, onBurgerMenuClick = {}, onTargetClick = {}, onLeaderBoardClick = {})
}