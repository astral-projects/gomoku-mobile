package gomoku.about.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.about.domain.About
import gomoku.about.domain.Section
import gomoku.about.ui.components.FooterLogo
import gomoku.home.domain.Home.GAME_NAME
import gomoku.shared.background.Background
import gomoku.shared.components.ExpandableCard
import gomoku.shared.components.TopNavHeader
import gomoku.shared.components.navigation.NavigationDrawer
import gomoku.shared.components.navigation.NavigationItem
import gomoku.shared.components.navigation.NavigationItemGroup
import gomoku.shared.theme.GomokuTheme
import kotlinx.coroutines.launch
import pdm.gomoku.R

// Config
private val sectionVerticalPadding = 10.dp
private val sectionPadding = 10.dp

/**
 * Represents the About screen main composable.
 * @param sections the [List] of [Section] to be displayed in the body.
 * @param toFindGameScreen callback to be executed when the user clicks on the respective navigation item.
 * @param toLeaderboardScreen callback to be executed when the user clicks on the respective navigation item.
 * @param onLogoutRequest callback to be executed when the user clicks on the respective navigation item.
 */
@Composable
fun AboutScreen(
    sections: List<Section>,
    toFindGameScreen: () -> Unit,
    toLeaderboardScreen: () -> Unit,
    onLogoutRequest: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var inDarkTheme by remember { mutableStateOf(false) }

    // data for the navigation drawer
    val aboutItem = NavigationItem(
        title = "About",
        selectedIconId = R.drawable.nav_about,
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
            NavigationItem(
                title = "Leaderboard",
                selectedIconId = R.drawable.nav_leaderboard,
                onClick = toLeaderboardScreen
            ),
            aboutItem
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
    // UI
    GomokuTheme(darkTheme = inDarkTheme) {
        NavigationDrawer(
            drawerState = drawerState,
            items = listOf(items, settings),
            selectedItem = aboutItem
        ) {

            Background(
                header = {
                    TopNavHeader(
                        title = GAME_NAME,
                        onBurgerMenuClick = {
                            scope.launch { drawerState.open() }
                        })
                },
                useBodySurface = false,
                footer = { FooterLogo() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(sectionPadding)
                ) {
                    sections.forEach { section ->
                        Row(
                            modifier = Modifier
                                .padding(vertical = sectionVerticalPadding)
                        ) {
                            ExpandableCard(
                                arrowColor = MaterialTheme.colorScheme.onSecondary,
                                titleColor = MaterialTheme.colorScheme.onSecondary,
                                leadingIconId = section.iconId,
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                title = section.title.value,
                                description = section.description.value
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun AboutScreenPreview() {
    AboutScreen(
        sections = About.sections,
        toFindGameScreen = {},
        toLeaderboardScreen = {},
        onLogoutRequest = {}
    )
}