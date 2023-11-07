package gomoku.shared.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import gomoku.shared.theme.GomokuTheme

/**
 * A [CenterAlignedTopAppBar] with a burger menu icon.
 * @param title The title of the header.
 * @param onBurgerMenuClick The callback to be executed when the burger menu is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavHeader(
    title: String,
    onBurgerMenuClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Row {
                HeaderText(text = title)
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBurgerMenuClick() }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
    )
}

@Composable
@Preview(showBackground = true)
fun TopNavHeaderPreview() {
    GomokuTheme {
        TopNavHeader(title = "Gomoku Royale", onBurgerMenuClick = {})
    }
}
