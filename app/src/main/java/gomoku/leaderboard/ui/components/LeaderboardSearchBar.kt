package gomoku.leaderboard.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import pdm.gomoku.R

// Config
private const val SEARCH_BAR_WIDTH_FACTOR = 0.9f

/**
 * A [SearchBar] that represents the search field in the leaderboard.
 * @param modifier The modifier to be applied to the search bar.
 * @param query The current query.
 * @param placeHolder The placeholder text.
 * @param onQueryChange The callback to be called when the query changes.
 * @param onClearSearch The callback to be called when the search is cleared.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    placeHolder: String?,
    onQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit
) = SearchBar(
    colors = SearchBarDefaults.colors(
        containerColor = MaterialTheme.colorScheme.secondary,
        inputFieldColors = inputFieldColors(
            cursorColor = MaterialTheme.colorScheme.inversePrimary,
        ),
    ),
    modifier = modifier
        .fillMaxWidth(SEARCH_BAR_WIDTH_FACTOR),
    placeholder = { Text(text = placeHolder ?: "", style = MaterialTheme.typography.bodyLarge) },
    query = query,
    onQueryChange = onQueryChange,
    active = false,
    onActiveChange = { },
    onSearch = { },
    leadingIcon = {
        Image(
            painter = painterResource(id = R.drawable.search),
            contentDescription = null,
        )
    },
    enabled = true,
    trailingIcon = {
        Icon(
            Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier.clickable { onClearSearch() }
        )
    },
    content = { }
)