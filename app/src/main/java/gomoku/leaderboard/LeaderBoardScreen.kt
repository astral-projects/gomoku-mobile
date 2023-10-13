package gomoku.leaderboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.GAME_NAME
import gomoku.ui.background.Background
import gomoku.ui.components.generic.SearchBar
import gomoku.ui.components.generic.TopNavHeader
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R

@Composable
fun LeaderBoardScreen(
    onSearch: (String) -> Unit = {},
    onBurgerMenuClick: () -> Unit = {}
) {
    GomokuTheme {
        Background(
            header = {
                TopNavHeader(title = GAME_NAME, onBurgerMenuClick = onBurgerMenuClick)
                SearchBar(
                    text = "Search a player...",
                    iconId = R.drawable.search,
                    onSearch = onSearch
                )
            },
            footer = {
                // TODO: Add footer of leaderboard
            }
        ) {
            // TODO: Add leaderboard body
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LeaderBoardScreenPreview() {
    LeaderBoardScreen(onSearch = {}, onBurgerMenuClick = {})
}