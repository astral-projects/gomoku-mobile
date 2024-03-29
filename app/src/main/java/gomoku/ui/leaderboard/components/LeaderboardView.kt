package gomoku.ui.leaderboard.components

import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import gomoku.domain.leaderboard.Leaderboard
import gomoku.domain.leaderboard.Term
import gomoku.domain.leaderboard.UserStats
import gomoku.domain.login.UserInfo
import gomoku.ui.leaderboard.LeaderBoardScreenState
import gomoku.ui.leaderboard.extractUsersWithRanking
import gomoku.ui.shared.background.Background
import gomoku.ui.shared.components.ClickableIcon
import gomoku.ui.shared.components.ThemedCircularProgressIndicator
import gomoku.ui.shared.components.TopNavBarWithBurgerMenu
import gomoku.ui.shared.components.navigationDrawer.NavigationDrawer
import gomoku.ui.shared.components.navigationDrawer.NavigationItem
import gomoku.ui.shared.components.navigationDrawer.NavigationItemGroup
import gomoku.ui.shared.dialogs.UserStatsDialog
import gomoku.ui.shared.theme.GomokuTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pdm.gomoku.R

// Config
private val footerIconHorizontalPadding = 10.dp
private val footerIconVerticalPadding = 10.dp
private const val LEADERBOARD_TABLE_HEIGHT_FACTOR = 0.85f
private const val FIRST_PAGE = 1
private const val LOAD_MORE_ITEMS_THRESHOLD = 3
private const val PAGE_SIZE = 20

/**
 * Represents the Leaderboard screen main composable.
 * @param state the state of the screen.
 * @param userInfo the logged user.
 * @param isDarkTheme the current theme of the app.
 * @param setDarkTheme the callback to be called when the user changes the theme.
 * @param getUserStats callback to get the user stats, given an id.
 * @param getItemsFromPage callback to get the items to be displayed in the leaderboard, given a page number.
 * @param onSearchRequest callback to be executed when a search is requested.
 * @param toFindGameScreen callback to be executed when the user clicks on the respective navigation item.
 * @param toAboutScreen callback to be executed when the user clicks on the respective navigation item.
 * @param onLogoutRequest callback to be executed when the user clicks on the respective navigation item.
 */
@Composable
fun LeaderboardView(
    userInfo: UserInfo,
    isDarkTheme: Boolean,
    setDarkTheme: (Boolean) -> Unit,
    state: LeaderBoardScreenState,
    getUserStats: (id: Int) -> Unit,
    getItemsFromPage: (page: Int) -> Unit,
    onSearchRequest: (term: Term) -> Unit,
    toFindGameScreen: () -> Unit,
    toAboutScreen: () -> Unit,
    onLogoutRequest: () -> Unit,
) {
    // search
    var wasQueryInitialized by rememberSaveable { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf("") }
    // list and pagination
    val lazyListState = rememberLazyListState()
    var page by rememberSaveable { mutableIntStateOf(FIRST_PAGE) }
    val scope = rememberCoroutineScope()
    // profile dialog
    var showUserProfileDialog by rememberSaveable { mutableStateOf(false) }
    // user info to be displayed in the profile dialog
    var userRankInfo by remember { mutableStateOf(UserStats(userInfo).toRankingInfo()) }
    var isSelfPositionEnabled by remember { mutableStateOf(false) }
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
                onClick = { setDarkTheme(!isDarkTheme) }
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
        val term = Term.toTermOrNull(query)
        if (term != null) {
            onSearchRequest(term)
        } else if (query == "") {
            // fetch first page only after query initialization with another value
            // avoiding fetching the first page when the screen is created
            // since that is already done in lifecycleScope of the corresponding activity
            if (wasQueryInitialized) {
                page = FIRST_PAGE
                getItemsFromPage(page)
            }
            wasQueryInitialized = true
        }
        lazyListState.scrollToItem(0)
    }
    // Observe scroll state to load more items
    LaunchedEffect(key1 = lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { itemIndex ->
                if (
                    state is LeaderBoardScreenState.Loaded || state is LeaderBoardScreenState.Idle
                    && itemIndex != null
                    && itemIndex >= page * PAGE_SIZE - LOAD_MORE_ITEMS_THRESHOLD
                ) {
                    Log.v("LeaderboardView", "fetching more items")
                    getItemsFromPage(++page)
                }
            }
    }
    // UI
    GomokuTheme(darkTheme = isDarkTheme) {
        // Evaluate if the profile dialog should be displayed
        if (showUserProfileDialog) {
            UserStatsDialog(
                userRankingInfo = userRankInfo,
                onDismissRequest = { showUserProfileDialog = false }
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
                            onClearSearch = {
                                if (query != "") {
                                    query = ""
                                    page = FIRST_PAGE
                                    getItemsFromPage(page)
                                    scope.launch {
                                        lazyListState.scrollToItem(0)
                                    }
                                }
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
                        if (state is LeaderBoardScreenState.Loading && page == FIRST_PAGE) {
                            ThemedCircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                        } else {
                            LeaderboardTable(
                                playersRankingInfo = state.extractUsersWithRanking(),
                                listState = lazyListState,
                                loading = state is LeaderBoardScreenState.Loading
                            ) {
                                userRankInfo = it
                                showUserProfileDialog = true
                            }
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
                        ToggleSelfPositionIcon(iconId = R.drawable.target) {
                            if (isSelfPositionEnabled) {
                                page = FIRST_PAGE
                                getItemsFromPage(page)
                                scope.launch {
                                    lazyListState.scrollToItem(0)
                                }
                            } else {
                                getUserStats(userInfo.id)
                            }
                            // toggle
                            isSelfPositionEnabled = !isSelfPositionEnabled
                        }
                        BackTopTheTopIcon(iconId = R.drawable.back_to_top) {
                            page = FIRST_PAGE
                            getItemsFromPage(page)
                            scope.launch {
                                lazyListState.scrollToItem(0)
                            }
                        }
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
