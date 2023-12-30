package gomoku.ui.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.domain.about.About
import gomoku.domain.about.Author
import gomoku.domain.about.Section
import gomoku.domain.home.Home.gameName
import gomoku.ui.about.components.FooterLogo
import gomoku.ui.shared.background.Background
import gomoku.ui.shared.components.ExpandableCard
import gomoku.ui.shared.components.TopNavBarWithBurgerMenu
import gomoku.ui.shared.components.navigationDrawer.NavigationDrawer
import gomoku.ui.shared.components.navigationDrawer.NavigationItem
import gomoku.ui.shared.components.navigationDrawer.NavigationItemGroup
import gomoku.ui.shared.theme.GomokuTheme
import kotlinx.coroutines.launch
import pdm.gomoku.R

// Config
private val sectionVerticalPadding = 10.dp
private val sectionPadding = 10.dp
private val descriptionPadding = 10.dp

/**
 * Represents the About screen main composable.
 * @param sections the [List] of [Section] to be displayed in the body.
 * @param toFindGameScreen callback to be executed when the user clicks on the respective navigation item.
 * @param toLeaderboardScreen callback to be executed when the user clicks on the respective navigation item.
 * @param onLogoutRequest callback to be executed when the user clicks on the respective navigation item.
 */
@Composable
fun AboutScreen(
    inDarkTheme: Boolean = false,
    sections: List<Section>,
    authors: List<Author>,
    setDarkTheme: (Boolean) -> Unit = {},
    toFindGameScreen: () -> Unit,
    toLeaderboardScreen: () -> Unit,
    onLogoutRequest: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val uriHandler = LocalUriHandler.current
    // data for the navigation drawer
    val aboutItem = NavigationItem(
        title = stringResource(id = R.string.nav_item_about),
        selectedIconId = R.drawable.nav_about,
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
            NavigationItem(
                title = stringResource(id = R.string.nav_item_leaderboard),
                selectedIconId = R.drawable.nav_leaderboard,
                onClick = toLeaderboardScreen
            ),
            aboutItem
        )
    )
    val settings = NavigationItemGroup(
        title = stringResource(id = R.string.nav_group_items_settings),
        items = listOf(
            NavigationItem(
                title = stringResource(id = R.string.nav_item_switch_theme),
                selectedIconId = R.drawable.nav_switch_theme,
                onClick = { setDarkTheme(!inDarkTheme) }
            ),
            NavigationItem(
                title = stringResource(id = R.string.nav_item_logout),
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
                    TopNavBarWithBurgerMenu(
                        title = stringResource(gameName),
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
                                title = stringResource(section.title.value)
                            ) {
                                if (section.description != null) {
                                    Text(
                                        text = stringResource(id = section.description.value),
                                        modifier = Modifier.padding(descriptionPadding),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.inversePrimary,
                                        textAlign = TextAlign.Left,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                } else {
                                    authors.forEach {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            IconButton(
                                                onClick = { uriHandler.openUri(it.githubUrl) },
                                                modifier = Modifier.paint(
                                                    painter = painterResource(id = R.drawable.github)
                                                )
                                            ) { }
                                            Text(
                                                text = it.name,
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = MaterialTheme.colorScheme.inversePrimary,
                                                textAlign = TextAlign.Left,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                        }
                                    }
                                }
                            }
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
        authors = About.authors,
        toFindGameScreen = {},
        toLeaderboardScreen = {},
        onLogoutRequest = {}
    )
}