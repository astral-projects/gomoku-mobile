package gomoku.shared.components.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.home.domain.Home.GAME_NAME
import gomoku.shared.components.HeaderText
import gomoku.shared.theme.GomokuTheme
import kotlinx.coroutines.launch
import pdm.gomoku.R

// Config
private val mainTitlePadding = 10.dp
private val horizontalItemPadding = 5.dp
private val verticalItemPadding = 2.dp

/**
 * A [ModalNavigationDrawer] that displays a list of [NavigationItem]s.
 * Such items can be grouped together using [NavigationItemGroup].
 * @param drawerState The state of the drawer.
 * @param items The list of [NavigationItemGroup]s to display.
 * @param selectedItem The currently selected [NavigationItem]. If null, no item will be selected.
 * @param content The content of the screen.
 */
@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    items: List<NavigationItemGroup>,
    selectedItem: NavigationItem? = null,
    content: @Composable () -> Unit
) {
    val itemStyle = MaterialTheme.typography.bodyLarge
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        scrimColor = MaterialTheme.colorScheme.secondary,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primary,
                drawerContentColor = MaterialTheme.colorScheme.background
            ) {
                Row(
                    modifier = Modifier.padding(mainTitlePadding),
                ) {
                    HeaderText(text = GAME_NAME)
                }
                Divider()
                items.forEach { itemGroup ->
                    itemGroup.title?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(mainTitlePadding),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.inversePrimary,
                        )
                    }
                    itemGroup.items.forEach { item ->
                        NavigationDrawerItem(
                            modifier = Modifier.padding(
                                horizontal = horizontalItemPadding,
                                vertical = verticalItemPadding
                            ),
                            icon = {
                                Image(
                                    painter = painterResource(id = item.selectedIconId),
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            selected = selectedItem == item,
                            onClick = {
                                scope.launch {
                                    item.onClick()
                                }
                            },
                            badge = {
                                item.badgeCount?.let { count ->
                                    Text(
                                        text = count.toString(),
                                        style = itemStyle
                                    )
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondary,
                                unselectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.inversePrimary,
                                unselectedTextColor = MaterialTheme.colorScheme.inversePrimary
                            )
                        )
                    }
                }
            }
        },
        content = content
    )
}

@Composable
@Preview(showBackground = true)
fun NavigationDrawerPreview() {
    val findGame = NavigationItem(
        title = "Find Game",
        selectedIconId = R.drawable.nav_find_game,
        onClick = {}
    )
    val items = NavigationItemGroup("Screens",
        listOf(
            findGame,
            NavigationItem(
                title = "Leaderboard",
                selectedIconId = R.drawable.nav_leaderboard,
                onClick = {}
            ),
            NavigationItem(
                title = "About",
                selectedIconId = R.drawable.nav_about,
                onClick = {}
            )
        )
    )
    val settings = NavigationItemGroup(
        title = "Settings",
        items = listOf(
            NavigationItem(
                title = "Switch Theme",
                selectedIconId = R.drawable.nav_switch_theme,
                onClick = {}
            ),
            NavigationItem(
                title = "Logout",
                selectedIconId = R.drawable.nav_logout,
                onClick = {}
            )
        ))
    GomokuTheme {
        NavigationDrawer(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
            selectedItem = findGame,
            items = listOf(items, settings)
        ) {
            Text("Content")
        }
    }
}