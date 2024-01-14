package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import gomoku.domain.leaderboard.PaginatedResult
import gomoku.domain.login.UserInfo
import gomoku.ui.leaderboard.LeaderBoardScreenState
import gomoku.ui.leaderboard.LeaderboardScreen
import gomoku.ui.shared.components.BurgerMenuButton
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuAboutButton
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuFindGameButton
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuLogoutButton
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuSwitchThemeButton
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class LeaderBoardScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun try_to_click_on_burger_menu_and_check_if_it_opens() {
        //Arrange
        composeTestRule.setContent {
            LeaderboardScreen(
                isDarkTheme = false,
                state = LeaderBoardScreenState.Loaded(PaginatedResult(emptyList())),
                userInfo = UserInfo(1, "test", "test", "test", 1),
                getItemsFromPage = {},
                getUserStats = {},
                onSearchRequest = {},
                toFindGameScreen = {},
                setDarkTheme = {},
                toAboutScreen = {},
                onLogoutRequest = {}
            )
        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuButton).performClick()
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_find_game() {
        var findGameWasCalled = false

        composeTestRule.setContent {
            LeaderboardScreen(
                isDarkTheme = false,
                state = LeaderBoardScreenState.Loaded(PaginatedResult(emptyList())),
                userInfo = UserInfo(1, "test", "test", "test", 1),
                getItemsFromPage = {},
                getUserStats = {},
                onSearchRequest = {},
                toFindGameScreen = { findGameWasCalled = true },
                setDarkTheme = {},
                toAboutScreen = {},
                onLogoutRequest = {}
            )
        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuFindGameButton).performClick()

        //Assert
        assertTrue(findGameWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_about() {
        var aboutWasCalled = false

        composeTestRule.setContent {
            LeaderboardScreen(
                isDarkTheme = false,
                state = LeaderBoardScreenState.Loaded(PaginatedResult(emptyList())),
                userInfo = UserInfo(1, "test", "test", "test", 1),
                getItemsFromPage = {},
                getUserStats = {},
                onSearchRequest = {},
                toFindGameScreen = {},
                setDarkTheme = {},
                toAboutScreen = { aboutWasCalled = true },
                onLogoutRequest = {}
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
            LeaderboardScreen(
                isDarkTheme = false,
                state = LeaderBoardScreenState.Loaded(PaginatedResult(emptyList())),
                userInfo = UserInfo(1, "test", "test", "test", 1),
                getItemsFromPage = {},
                getUserStats = {},
                onSearchRequest = {},
                toFindGameScreen = {},
                setDarkTheme = {},
                toAboutScreen = {},
                onLogoutRequest = { logoutWasCalled = true }
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
            LeaderboardScreen(
                isDarkTheme = false,
                state = LeaderBoardScreenState.Loaded(PaginatedResult(emptyList())),
                userInfo = UserInfo(1, "test", "test", "test", 1),
                getItemsFromPage = {},
                getUserStats = {},
                onSearchRequest = {},
                toFindGameScreen = {},
                setDarkTheme = {},
                toAboutScreen = {},
                onLogoutRequest = {}
            )
        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).performClick()

        //Assert
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).assertExists()

    }

}
