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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import gomoku.leaderboard.domain.Leaderboard
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.leaderboard.domain.RankingInfo
import gomoku.leaderboard.domain.Term
import gomoku.leaderboard.ui.components.LeaderboardSearchBar
import gomoku.leaderboard.ui.components.LeaderboardTable
import gomoku.leaderboard.user.ui.UserDialog
import gomoku.shared.background.Background
import gomoku.shared.components.ClickableIcon
import gomoku.shared.components.TopNavBarWithBurgerMenu
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

// in terms of visible items, load more items when there are x items left in the list
private const val LOAD_MORE_THRESHOLD = 3


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
fun LeaderboardView(
    searchingLeaderboard: Boolean,
    rankingInfo: RankingInfo,
    getItemsFromPage: (page: Int) -> List<RankingInfo>,
    onSearchRequest: (Term) -> List<RankingInfo>,
    toFindGameScreen: () -> Unit,
    toAboutScreen: () -> Unit,
    onLogoutRequest: () -> Unit,
) {
    // search
    var query by rememberSaveable { mutableStateOf("") }
    // list and pagination
    val lazyListState = rememberLazyListState()
    var page by remember { mutableIntStateOf(FIRST_PAGE) }
    var isLoadingPages by remember { mutableStateOf(false) }
    var currentItems by remember { mutableStateOf(getItemsFromPage(page)) }
    //var currentItems = getItemsFromPage(page)
    // profile dialog
    var isProfileDialogOpen by rememberSaveable { mutableStateOf(false) }
    // user info to be displayed in the profile dialog
    var userInfo by remember {
        mutableStateOf(
            RankingInfo(
                PlayerInfo("", 0),
                1,
                1,
                1,
                1,
                1,
                1
            )
        )
    }
    // others
    val scope = rememberCoroutineScope()
    var isSelfPositionEnabled by rememberSaveable { mutableStateOf(false) }
    var inDarkTheme by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // data for the navigation drawer
    val leaderboardItem = NavigationItem(
        title = stringResource(id = R.string.nav_item_leaderboard),
        selectedIconId = R.drawable.nav_leaderboard,
        onClick = {}
    )
    val items = NavigationItemGroup(
        title = stringResource(id = R.string.nav_group_items_screens),
        items = listOf(
            NavigationItem(
                title = stringResource(id = R.string.nav_item_find_game),
                selectedIconId = R.drawable.nav_find_game,
                onClick = toFindGameScreen
            ),
            leaderboardItem,
            NavigationItem(
                title = stringResource(id = R.string.nav_item_about),
                selectedIconId = R.drawable.nav_about,
                onClick = toAboutScreen
            )
        )
    )
    val settings = NavigationItemGroup(
        title = stringResource(id = R.string.nav_group_items_settings),
        items = listOf(
            NavigationItem(
                title = stringResource(id = R.string.nav_item_switch_theme),
                selectedIconId = R.drawable.nav_switch_theme,
                onClick = { inDarkTheme = !inDarkTheme }
            ),
            NavigationItem(
                title = stringResource(id = R.string.nav_item_logout),
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
            currentItems = getItemsFromPage(page)
            // Position the list at the top when the page is loaded
            lazyListState.scrollToItem(0)
        } else {
            currentItems += getItemsFromPage(page)
        }
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

        // Evaluate if the profile dialog should be displayed
        if (isProfileDialogOpen) {
            UserDialog(
                rankingInfo = userInfo,
                onDismissRequest = { isProfileDialogOpen = false }
            )
        }

        NavigationDrawer(
            drawerState = drawerState,
            items = listOf(items, settings),
            selectedItem = leaderboardItem
        ) {
            Background(
                header = {
                    TopNavBarWithBurgerMenu(
                        title = stringResource(Leaderboard.title),
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
                            placeHolder = stringResource(Leaderboard.searchBarPlaceHolder),
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
                        if (searchingLeaderboard) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxHeight(0.5f)
                                    .align(Alignment.CenterHorizontally),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.8f)
                            )
                        } else {
                            LeaderboardTable(
                                playersRankingInfo = currentItems,
                                listState = lazyListState,
                                loading = isLoadingPages,
                                onClick = {
                                    userInfo = it
                                    isProfileDialogOpen = true
                                }
                            )
                        }
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
                                currentItems =
                                    onSearchRequest(Term(rankingInfo.playerInfo.name))
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
