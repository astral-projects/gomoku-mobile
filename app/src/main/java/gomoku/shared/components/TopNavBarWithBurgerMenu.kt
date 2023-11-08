package gomoku.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import pdm.gomoku.R

/**
 * A [CenterAlignedTopAppBar] with a burger menu icon.
 * @param title The title of the header.
 * @param onBurgerMenuClick The callback to be executed when the burger menu is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBarWithBurgerMenu(
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
                Image(
                    painter = painterResource(id = R.drawable.burger_bar),
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
private fun TopNavHeaderPreview() {
    TopNavBarWithBurgerMenu(title = "Gomoku Royale", onBurgerMenuClick = {})
}
