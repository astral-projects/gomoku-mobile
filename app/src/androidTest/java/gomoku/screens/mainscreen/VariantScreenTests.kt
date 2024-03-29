package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import gomoku.domain.game.board.BoardSize
import gomoku.domain.login.UserInfo
import gomoku.domain.variant.OpeningRule
import gomoku.domain.variant.VariantConfig
import gomoku.domain.variant.VariantName
import gomoku.ui.shared.components.BurgerMenuButton
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuAboutButton
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuFindGameButton
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuLogoutButton
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuSwitchThemeButton
import gomoku.ui.variant.VariantScreen
import gomoku.ui.variant.VariantScreenState
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

const val variantSubmitButtonText = "Play"

class VariantScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    val variants = listOf(
        VariantConfig(
            id = 1,
            name = VariantName.FREESTYLE,
            boardSize = BoardSize.FIFTEEN,
            openingRule = OpeningRule.PRO
        ),
        VariantConfig(
            id = 2,
            name = VariantName.OMOK,
            boardSize = BoardSize.NINETEEN,
            openingRule = OpeningRule.LONG_PRO
        ),
        VariantConfig(
            id = 3,
            name = VariantName.PENTE,
            boardSize = BoardSize.FIFTEEN,
            openingRule = OpeningRule.PRO
        ),
        VariantConfig(
            id = 4,
            name = VariantName.RENJU,
            boardSize = BoardSize.FIFTEEN,
            openingRule = OpeningRule.PRO
        ),
        VariantConfig(
            id = 5,
            name = VariantName.CARO,
            boardSize = BoardSize.NINETEEN,
            openingRule = OpeningRule.PRO
        )
    )

    @Test
    fun trying_to_click_on_the_navigation_variant_without_any_variant_selected() {
        // Arrange
        var variantRequested = false
        composeTestRule.setContent {
            VariantScreen(
                onPlayRequest = { _ ->
                    variantRequested = true
                },
                state = VariantScreenState.FetchVariants(variants, true),
                toLeaderboardScreen = {},
                toAboutScreen = {},
                onLogoutRequest = {},
                onLobbyExitRequest = {},
                setDarkTheme = {},
                userInfo = UserInfo(1, "test", "test", "test", 2)
            )
        }
        // Act
        composeTestRule.onNodeWithTag(variantSubmitButtonText).performClick()
        assertFalse(variantRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_variant_with_FreeStyle_variant_selected() {
        // Arrange
        var variantRequested = false

        composeTestRule.setContent {
            VariantScreen(
                onPlayRequest = { _ ->
                    variantRequested = true
                },
                state = VariantScreenState.FetchVariants(variants, true),
                toLeaderboardScreen = {},
                toAboutScreen = {},
                onLogoutRequest = {},
                onLobbyExitRequest = {},
                setDarkTheme = {},
                userInfo = UserInfo(1, "test", "test", "test", 2)
            )
        }
        // Act
        composeTestRule.onNodeWithTag(VariantName.FREESTYLE.name).performClick()
        composeTestRule.onNodeWithTag(variantSubmitButtonText).performClick()
        // Assert
        assertTrue(variantRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_variant_with_Pente_variant_selected() {
        // Arrange
        var variantRequested = false

        composeTestRule.setContent {
            VariantScreen(
                onPlayRequest = { _ ->
                    variantRequested = true
                },
                state = VariantScreenState.FetchVariants(variants, true),
                toLeaderboardScreen = {},
                toAboutScreen = {},
                onLogoutRequest = {},
                onLobbyExitRequest = {},
                setDarkTheme = {},
                userInfo = UserInfo(1, "test", "test", "test", 2)
            )
        }
        // Act
        composeTestRule.onNodeWithTag(VariantName.PENTE.name).performClick()
        composeTestRule.onNodeWithTag(variantSubmitButtonText).performClick()
        assertTrue(variantRequested)
    }


    @Test
    fun try_to_click_on_burger_menu_and_check_if_it_opens() {
        //Arrange
        composeTestRule.setContent {
            VariantScreen(
                onPlayRequest = { _ ->
                },
                state = VariantScreenState.FetchVariants(variants, true),
                toLeaderboardScreen = {},
                toAboutScreen = {},
                onLogoutRequest = {},
                onLobbyExitRequest = {},
                setDarkTheme = {},
                userInfo = UserInfo(1, "test", "test", "test", 2)
            )

        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuButton).performClick()
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_leaderboard() {
        var leaderboardWasCalled = false

        composeTestRule.setContent {
            VariantScreen(
                onPlayRequest = { _ ->
                },
                state = VariantScreenState.FetchVariants(variants, true),
                toLeaderboardScreen = { leaderboardWasCalled = true },
                toAboutScreen = {},
                onLogoutRequest = {},
                onLobbyExitRequest = {},
                setDarkTheme = {},
                userInfo = UserInfo(1, "test", "test", "test", 2)
            )

        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuFindGameButton).performClick()

        //Assert
        assertFalse(leaderboardWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_about() {
        var aboutWasCalled = false

        composeTestRule.setContent {
            VariantScreen(
                onPlayRequest = { _ ->
                },
                state = VariantScreenState.FetchVariants(variants, true),
                toLeaderboardScreen = {},
                toAboutScreen = { aboutWasCalled = true },
                onLogoutRequest = {},
                onLobbyExitRequest = {},
                setDarkTheme = {},
                userInfo = UserInfo(1, "test", "test", "test", 2)
            )
        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuAboutButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuAboutButton).performClick()

        //Assert
        assertTrue(aboutWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_logout() {
        var logoutWasCalled = false

        composeTestRule.setContent {
            VariantScreen(
                onPlayRequest = { _ ->
                },
                state = VariantScreenState.FetchVariants(variants, true),
                toLeaderboardScreen = { },
                toAboutScreen = {},
                onLogoutRequest = { logoutWasCalled = true },
                onLobbyExitRequest = {},
                setDarkTheme = {},
                userInfo = UserInfo(1, "test", "test", "test", 2)
            )

        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuLogoutButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuLogoutButton).performClick()

        //Assert
        assertTrue(logoutWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_switch_theme() {
        //Arrange
        composeTestRule.setContent {
            VariantScreen(
                onPlayRequest = { _ ->
                },
                state = VariantScreenState.FetchVariants(variants, true),
                toLeaderboardScreen = { },
                toAboutScreen = {},
                onLogoutRequest = {},
                onLobbyExitRequest = {},
                setDarkTheme = {},
                userInfo = UserInfo(1, "test", "test", "test", 2)
            )
        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).performClick()

        //Assert
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).assertExists()

    }

}