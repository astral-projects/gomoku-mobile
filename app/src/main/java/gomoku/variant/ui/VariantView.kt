package gomoku.variant.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
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
import gomoku.home.domain.Home
import gomoku.register.ui.components.FooterBubbles
import gomoku.shared.background.Background
import gomoku.shared.components.HeaderText
import gomoku.shared.components.SubmitButton
import gomoku.shared.components.TopNavBarWithBurgerMenu
import gomoku.shared.components.navigation.NavigationDrawer
import gomoku.shared.components.navigation.NavigationItem
import gomoku.shared.components.navigation.NavigationItemGroup
import gomoku.shared.theme.GomokuTheme
import gomoku.variant.domain.Variant
import gomoku.variant.domain.VariantConfig
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
fun VariantView(
    inDarkTheme: Boolean,
    variantScreenState: VariantScreenState,
    onSubmit: (variantConfig: VariantConfig) -> Unit,
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
                        // Loading component
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxHeight(0.5f)
                                .align(Alignment.CenterHorizontally),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.8f)
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
                        if (variantScreenState == VariantScreenState.LoadingGameMatch) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxHeight(0.5f)
                                    .align(Alignment.CenterVertically),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.8f)
                            )
                        } else {
                            SubmitButton(
                                enable = selectedOption != null,
                                onButtonText = stringResource(Variant.submitButtonText),
                                onClick = { selectedOption?.let { onSubmit(it) } }
                            )
                        }
                    }
                }
            }
        }
    }
}