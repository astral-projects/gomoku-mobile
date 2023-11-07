package gomoku.variant.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.game.domain.board.BoardSize
import gomoku.home.domain.Home.GAME_NAME
import gomoku.register.ui.components.FooterBubbles
import gomoku.shared.background.Background
import gomoku.shared.components.HeaderText
import gomoku.shared.components.SubmitButton
import gomoku.shared.components.TopNavHeader
import gomoku.shared.components.navigation.NavigationDrawer
import gomoku.shared.components.navigation.NavigationItem
import gomoku.shared.components.navigation.NavigationItemGroup
import gomoku.shared.theme.GomokuTheme
import gomoku.variant.domain.OpeningRule
import gomoku.variant.domain.Variant
import gomoku.variant.domain.VariantConfig
import gomoku.variant.domain.VariantName
import gomoku.variant.ui.components.VariantTable
import kotlinx.coroutines.launch
import pdm.gomoku.R

// Config
private val variantHeaderSpacerWidth = 12.dp
private val variantSurfaceVerticalPadding = 15.dp

/**
 * Represents the Variant screen main composable.
 * @param onSubmit the callback to be called when the submit button is clicked.
 * @param variants the [List] of [VariantConfig]s to be displayed in the body.
 * @param toLeaderboardScreen callback to be executed when the user clicks on the respective navigation item.
 * @param toAboutScreen callback to be executed when the user clicks on the respective navigation item.
 * @param onLogoutRequest callback to be executed when the user clicks on the respective navigation item.
 */
@Composable
fun VariantScreen(
    onSubmit: (VariantConfig) -> Unit,
    variants: List<VariantConfig>,
    toLeaderboardScreen: () -> Unit,
    toAboutScreen: () -> Unit,
    onLogoutRequest: () -> Unit
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf<VariantConfig?>(null) }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var inDarkTheme by remember { mutableStateOf(false) }
    // data for the navigation drawer
    val findGameItem = NavigationItem(
        title = "Find Game",
        selectedIconId = R.drawable.nav_find_game,
        onClick = {}
    )
    val items = NavigationItemGroup(
        title = "Screens",
        items = listOf(
            findGameItem,
            NavigationItem(
                title = "Leaderboard",
                selectedIconId = R.drawable.nav_leaderboard,
                onClick = toLeaderboardScreen
            ),
            NavigationItem(
                title = "About",
                selectedIconId = R.drawable.nav_about,
                onClick = toAboutScreen
            )
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
            selectedItem = findGameItem
        ) {
            Background(
                header = {
                    TopNavHeader(
                        title = GAME_NAME,
                        onBurgerMenuClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    )
                },
                footer = { FooterBubbles() }
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = variantSurfaceVerticalPadding)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HeaderText(text = Variant.TITLE)
                        Spacer(modifier = Modifier.width(variantHeaderSpacerWidth))
                        Image(
                            painter = painterResource(id = R.drawable.book),
                            contentScale = ContentScale.Inside,
                            contentDescription = null
                        )
                    }
                    VariantTable(
                        variants = variants,
                        selectedOption = selectedOption,
                        onOptionSelected = onOptionSelected
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SubmitButton(
                            enable = selectedOption != null,
                            onButtonText = Variant.SUBMIT_BUTTON_TEXT,
                            onClick = { selectedOption?.let { onSubmit(it) } }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun VariantScreenPreview() {
    VariantScreen(
        variants = listOf(
            VariantConfig(
                name = VariantName.FREESTYLE,
                boardSize = BoardSize.FIFTEEN,
                openingRule = OpeningRule.PRO
            ),
            VariantConfig(
                name = VariantName.OMOK,
                boardSize = BoardSize.NINETEEN,
                openingRule = OpeningRule.LONG_PRO
            ),
            VariantConfig(
                name = VariantName.PENTE,
                boardSize = BoardSize.FIFTEEN,
                openingRule = OpeningRule.PRO
            ),
            VariantConfig(
                name = VariantName.RENJU,
                boardSize = BoardSize.FIFTEEN,
                openingRule = OpeningRule.PRO
            ),
            VariantConfig(
                name = VariantName.CARO,
                boardSize = BoardSize.NINETEEN,
                openingRule = OpeningRule.PRO
            ),
        ),
        onSubmit = {},
        toLeaderboardScreen = {},
        toAboutScreen = {},
        onLogoutRequest = {}
    )
}

@Composable
@Preview
private fun VariantScreenEmptyPreview() {
    VariantScreen(
        onSubmit = {},
        variants = emptyList(),
        toLeaderboardScreen = {},
        toAboutScreen = {},
        onLogoutRequest = {}
    )
}