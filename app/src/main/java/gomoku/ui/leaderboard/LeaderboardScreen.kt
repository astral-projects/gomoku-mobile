package gomoku.ui.leaderboard

import androidx.compose.runtime.Composable
import gomoku.domain.IOState
import gomoku.domain.leaderboard.Term
import gomoku.domain.leaderboard.UserStats
import gomoku.ui.leaderboard.components.LeaderboardView

/**
 * Represents the Leaderboard screen main composable.
 * @param state the state of the screen.
 * @param onSearchRequest callback to be executed when the user types on the search bar.
 * @param getUserStats callback to be executed when the user clicks on a user.
 * @param getItemsFromPage callback to be executed when the user scrolls to the bottom of the list.
 * @param toFindGameScreen callback to be executed when the user clicks on the respective navigation item.
 * @param toAboutScreen callback to be executed when the user clicks on the respective navigation item.
 * @param onLogoutRequest callback to be executed when the user clicks on the respective navigation item.
 */
@Composable
fun LeaderboardScreen(
    state: IOState<List<UserStats>>,
    onSearchRequest: (term: Term) -> Unit,
    getUserStats: (id: Int) -> Unit,
    getItemsFromPage: (page: Int) -> Unit,
    toFindGameScreen: () -> Unit,
    toAboutScreen: () -> Unit,
    onLogoutRequest: () -> Unit,
) {
    LeaderboardView(
        state = state,
        getUserStats = getUserStats,
        getItemsFromPage = getItemsFromPage,
        onSearchRequest = onSearchRequest,
        toFindGameScreen = toFindGameScreen,
        toAboutScreen = toAboutScreen,
        onLogoutRequest = onLogoutRequest,
        inDarkTheme = false,
        setDarkTheme = { },
    )
}