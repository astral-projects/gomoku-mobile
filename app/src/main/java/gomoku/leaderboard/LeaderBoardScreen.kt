package gomoku.leaderboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.lib.CenteredHeader
import gomoku.ui.lib.SearchBar
import gomoku.ui.screens.background.Background
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R

@Composable
fun LeaderBoardScreen(onSearch: (String) -> Unit = {}) {
    GomokuTheme {
        Background(
            header = {
                CenteredHeader(text = "Leaderboard")
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
    LeaderBoardScreen()
}