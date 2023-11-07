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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.leaderboard.domain.Leaderboard
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.leaderboard.domain.RankingInfo
import gomoku.leaderboard.domain.Term
import gomoku.leaderboard.ui.components.LeaderboardSearchBar
import gomoku.leaderboard.ui.components.LeaderboardTable
import gomoku.shared.background.Background
import gomoku.shared.components.ClickableIcon
import gomoku.shared.components.TopNavHeader
import gomoku.shared.components.navigation.NavigationDrawer
import gomoku.shared.components.navigation.NavigationItem
import gomoku.shared.components.navigation.NavigationItemGroup
import gomoku.shared.theme.GomokuTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pdm.gomoku.R

// Config
private val footerIconHorizontalPadding = 10.dp
private val footerIconVerticalPadding = 10.dp
private const val LEADERBOARD_TABLE_HEIGHT_FACTOR = 0.85f
private const val SEARCH_DELAY = 500L
private const val FIRST_PAGE = 1
// in terms of visible items, load more when there are x items left
private const val LOAD_MORE_THRESHOLD = 2

/**
 * Represents the Leaderboard screen main composable.
 * @param playerInfo the logged in player information.
 * @param getItemsFromPage callback to get the items to be displayed in the leaderboard, given a page number.
 * @param onSearchRequest callback to be executed when a search is requested.
 * @param toFindGameScreen callback to be executed when the user clicks on the respective navigation item.
 * @param toAboutScreen callback to be executed when the user clicks on the respective navigation item.
 * @param onLogoutRequest callback to be executed when the user clicks on the respective navigation item.
 */
@Composable
fun LeaderboardScreen(
    playerInfo: PlayerInfo,
    getItemsFromPage: (page: Int) -> List<RankingInfo>,
    onSearchRequest: (Term) -> List<RankingInfo>,
    toFindGameScreen: () -> Unit,
    toAboutScreen: () -> Unit,
    onLogoutRequest: () -> Unit
) {
    // search
    var query by rememberSaveable { mutableStateOf("") }
    // list and pagination
    val lazyListState = rememberLazyListState()
    var page by remember { mutableIntStateOf(FIRST_PAGE) }
    var isLoadingPages by remember { mutableStateOf(false) }
    var currentItems by remember { mutableStateOf(getItemsFromPage(page)) }
    // others
    val scope = rememberCoroutineScope()
    var isSelfPositionEnabled by rememberSaveable { mutableStateOf(false) }
    var inDarkTheme by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // data for the navigation drawer
    val leaderboardItem = NavigationItem(
        title = "Leaderboard",
        selectedIconId = R.drawable.nav_leaderboard,
        onClick = {}
    )
    val items = NavigationItemGroup(
        title = "Screens",
        items = listOf(
            NavigationItem(
                title = "Find Game",
                selectedIconId = R.drawable.nav_find_game,
                onClick = toFindGameScreen
            ),
            leaderboardItem,
            NavigationItem(
                title = "About",
                selectedIconId = R.drawable.nav_about,
                onClick = toAboutScreen
            )
        )
    )
    val settings = NavigationItemGroup(
        title = "Settings",
        items = listOf(
            NavigationItem(
                title = "Switch Theme",
                selectedIconId = R.drawable.nav_switch_theme,
                onClick = { inDarkTheme = !inDarkTheme }
            ),
            NavigationItem(
                title = "Logout",
                selectedIconId = R.drawable.nav_logout,
                onClick = onLogoutRequest
            )
        )
    )
    // launched effects
    LaunchedEffect(key1 = query) {
        delay(SEARCH_DELAY)
        val term = Term.toTermOrNull(query)
        currentItems = if (term != null) {
            onSearchRequest(term)
        } else {
            getItemsFromPage(page)
        }
    }
    LaunchedEffect(key1 = page) {
        isLoadingPages = true
        // Simulate a network request (when using dummy data)
        delay(2000)
        if (page == FIRST_PAGE) {
            currentItems = getItemsFromPage(page);
            // Position the list at the top when the page is loaded
            lazyListState.scrollToItem(0)
        } else
            currentItems += getItemsFromPage(page)
        isLoadingPages = false
    }
    // Observe scroll state to load more items
    LaunchedEffect(key1 = lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { itemIndex ->
                if (!isLoadingPages && itemIndex != null && itemIndex >= currentItems.size - LOAD_MORE_THRESHOLD) {
                    page++
                }
            }
    }
    // UI
    GomokuTheme(darkTheme = inDarkTheme) {
        NavigationDrawer(
            drawerState = drawerState,
            items = listOf(items, settings),
            selectedItem = leaderboardItem
        ) {
            Background(
                header = {
                    TopNavHeader(
                        title = Leaderboard.TITLE,
                        onBurgerMenuClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
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
                            onClearSearch = { query = ""; page = FIRST_PAGE },
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
                            playersRankingInfo = currentItems,
                            listState = lazyListState,
                            loading = isLoadingPages
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
                            if (isSelfPositionEnabled) {
                                page = FIRST_PAGE
                                currentItems = getItemsFromPage(FIRST_PAGE)
                            } else {
                                currentItems = onSearchRequest(Term(playerInfo.name))
                            }
                            // toggle
                            isSelfPositionEnabled = !isSelfPositionEnabled
                        })
                        BackTopTheTopIcon(iconId = R.drawable.back_to_top, onClick = {
                            page = FIRST_PAGE
                        })
                    }
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
    val nPlayers = 200
    val playersRankingInfo = Leaderboard.generateRankingInfo(nPlayers)
    LeaderboardScreen(
        playerInfo = Leaderboard.fakePlayers.first(),
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
    )
}