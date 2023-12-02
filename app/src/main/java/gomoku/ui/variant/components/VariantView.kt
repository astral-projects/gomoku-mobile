package gomoku.ui.variant.components

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import gomoku.domain.home.Home
import gomoku.domain.login.UserInfo
import gomoku.domain.variant.Variant
import gomoku.domain.variant.VariantConfig
import gomoku.ui.register.components.FooterBubbles
import gomoku.ui.shared.background.Background
import gomoku.ui.shared.components.HeaderText
import gomoku.ui.shared.components.SubmitButton
import gomoku.ui.shared.components.ThemedCircularProgressIndicator
import gomoku.ui.shared.components.TopNavBarWithBurgerMenu
import gomoku.ui.shared.components.navigationDrawer.NavigationDrawer
import gomoku.ui.shared.components.navigationDrawer.NavigationItem
import gomoku.ui.shared.components.navigationDrawer.NavigationItemGroup
import gomoku.ui.shared.popups.WaitingOpponentPopup
import gomoku.ui.shared.theme.GomokuTheme
import gomoku.ui.variant.VariantScreenState
import kotlinx.coroutines.launch
import pdm.gomoku.R

// Config
private val variantHeaderSpacerWidth = 12.dp
private val variantSurfaceVerticalPadding = 15.dp
private val ThemedCircularProgressIndicatorPadding = 5.dp

/**
 * Represents the Variant screen main composable.
 * @param userInfo the [UserInfo] of the logged-in user.
 * @param inDarkTheme whether the app is in dark theme or not.
 * @param variantScreenState the [VariantScreenState] of the screen.
 * @param onPlayRequest the callback to be called when the submit button is clicked.
 * @param onLobbyExitRequest the callback to be called when the user wants to leave the lobby.
 * @param variants the [List] of [VariantConfig]s to be displayed in the body.
 * @param toLeaderboardScreen callback to be executed when the user clicks on the respective navigation item.
 * @param toAboutScreen callback to be executed when the user clicks on the respective navigation item.
 * @param onLogoutRequest callback to be executed when the user clicks on the respective navigation item.
 */
@Composable
fun VariantView(
    userInfo: UserInfo,
    inDarkTheme: Boolean,
    variantScreenState: VariantScreenState,
    onPlayRequest: (variantConfig: VariantConfig) -> Unit,
    onLobbyExitRequest: () -> Unit,
    variants: List<VariantConfig>,
    setDarkTheme: (Boolean) -> Unit,
    toLeaderboardScreen: () -> Unit,
    toAboutScreen: () -> Unit,
    onLogoutRequest: () -> Unit
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf<VariantConfig?>(null) }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // data for the navigation drawer
    val findGameItem = NavigationItem(
        title = stringResource(id = R.string.nav_item_find_game),
        selectedIconId = R.drawable.nav_find_game,
        onClick = {}
    )
    val items = NavigationItemGroup(
        title = stringResource(id = R.string.nav_group_items_screens),
        items = listOf(
            findGameItem,
            NavigationItem(
                title = stringResource(id = R.string.nav_item_leaderboard),
                selectedIconId = R.drawable.nav_leaderboard,
                onClick = toLeaderboardScreen
            ),
            NavigationItem(
                title = stringResource(id = R.string.nav_item_about),
                selectedIconId = R.drawable.nav_about,
                onClick = toAboutScreen
            )
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
            selectedItem = findGameItem
        ) {
            Background(
                header = {
                    TopNavBarWithBurgerMenu(
                        title = stringResource(Home.gameName),
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
                        HeaderText(text = stringResource(id = Variant.title))
                        Spacer(modifier = Modifier.width(variantHeaderSpacerWidth))
                        Image(
                            painter = painterResource(id = R.drawable.book),
                            contentScale = ContentScale.Inside,
                            contentDescription = null
                        )
                    }
                    if (variantScreenState == VariantScreenState.LoadingVariants) {
                        ThemedCircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(ThemedCircularProgressIndicatorPadding)
                        )
                    } else {
                        VariantTable(
                            variants = variants,
                            selectedOption = selectedOption,
                            onOptionSelected = onOptionSelected
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        when (variantScreenState) {
                            VariantScreenState.LoadingGameMatch -> {
                                ThemedCircularProgressIndicator()
                            }

                            VariantScreenState.WaitingInLobby -> {
                                WaitingOpponentPopup(
                                    playerIconId = userInfo.iconId,
                                    onDismissRequest = onLobbyExitRequest
                                )
                            }

                            else -> {
                                SubmitButton(
                                    enable = selectedOption != null,
                                    onButtonText = stringResource(Variant.submitButtonText),
                                    onClick = { selectedOption?.let { onPlayRequest(it) } }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}