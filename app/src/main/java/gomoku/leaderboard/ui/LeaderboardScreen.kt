package gomoku.leaderboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.leaderboard.domain.Leaderboard
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.leaderboard.domain.RankingInfo
import gomoku.leaderboard.ui.components.LeaderboardSearchBar
import gomoku.leaderboard.ui.components.LeaderboardTable
import gomoku.ui.background.Background
import gomoku.ui.components.ClickableIcon
import gomoku.ui.components.TopNavHeader
import gomoku.ui.theme.GomokuTheme
import kotlinx.coroutines.launch
import pdm.gomoku.R

// Config
private val footerIconHorizontalPadding = 10.dp
private val footerIconVerticalPadding = 10.dp
private const val LEADERBOARD_TABLE_HEIGHT_FACTOR = 0.85f

/**
 * Represents the Leaderboard screen main composable.
 * @param playerInfo the logged in player information.
 * @param playersRankingInfo list of players ranking information.
 * @param onSearchRequest callback to be executed when the search request is made.
 * @param onBurgerMenuClick callback to be executed when the burger menu is clicked.
 */
@Composable
fun LeaderboardScreen(
    playerInfo: PlayerInfo,
    playersRankingInfo: List<RankingInfo>,
    onBurgerMenuClick: () -> Unit = {},
) {
    val handler = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    var query by rememberSaveable { mutableStateOf("") }
    var filteredPlayersRankingInfo by rememberSaveable { mutableStateOf(playersRankingInfo) }
    var isSelfPositionEnabled by rememberSaveable { mutableStateOf(false) }
    GomokuTheme {
        Background(
            header = {
                TopNavHeader(
                    title = Leaderboard.TITLE,
                    onBurgerMenuClick = onBurgerMenuClick
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LeaderboardSearchBar(
                        query = query,
                        placeHolder = Leaderboard.SEARCH_BAR_PLACEHOLDER,
                        onQueryChange = { query = it },
                        onSearchRequest = { term ->
                            filteredPlayersRankingInfo = playersRankingInfo
                                .filter {
                                    it.playerInfo.name.contains(
                                        term.value,
                                        ignoreCase = true
                                    )
                                }
                        },
                        onClearSearch = {
                            query = ""; filteredPlayersRankingInfo = playersRankingInfo
                        },
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
                        .fillMaxHeight(LEADERBOARD_TABLE_HEIGHT_FACTOR)
                        .align(Alignment.TopCenter)
                ) {
                    LeaderboardTable(
                        playersRankingInfo = filteredPlayersRankingInfo,
                        listState = lazyListState
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(
                            horizontal = footerIconHorizontalPadding,
                            vertical = footerIconVerticalPadding
                        )
                ) {
                    ToggleSelfPositionIcon(iconId = R.drawable.target, onClick = {
                        filteredPlayersRankingInfo = if (isSelfPositionEnabled) {
                            playersRankingInfo
                        } else {
                            playersRankingInfo.filter { it.playerInfo == playerInfo }
                        }
                        isSelfPositionEnabled = !isSelfPositionEnabled
                    })
                    BackTopTheTopIcon(iconId = R.drawable.back_to_top, onClick = {
                        handler.launch {
                            // Scroll the LazyColumn to the top
                            lazyListState.animateScrollToItem(index = 0)
                        }
                    })
                }
            }
        }
    }
}

@Composable
private fun ToggleSelfPositionIcon(iconId: Int, onClick: () -> Unit) =
    ClickableIcon(iconId = iconId, onClick = onClick)

@Composable
private fun BackTopTheTopIcon(iconId: Int, onClick: () -> Unit) =
    ClickableIcon(iconId = iconId, onClick = onClick)

@Composable
@Preview(showBackground = true)
private fun LeaderboardScreenPreview() {
    LeaderboardScreen(
        playerInfo = Leaderboard.fakePlayers.first(),
        playersRankingInfo = Leaderboard.generateFakeRankingInfo(200)
    )
}