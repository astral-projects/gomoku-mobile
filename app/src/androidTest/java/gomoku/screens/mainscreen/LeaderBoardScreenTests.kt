package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import gomoku.Loaded
import gomoku.leaderboard.domain.Leaderboard
import gomoku.leaderboard.ui.LeaderboardScreen
import gomoku.shared.components.BurgerMenuButton
import gomoku.shared.components.navigation.BurgerMenuAboutButton
import gomoku.shared.components.navigation.BurgerMenuFindGameButton
import gomoku.shared.components.navigation.BurgerMenuLogoutButton
import gomoku.shared.components.navigation.BurgerMenuSwitchThemeButton
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class LeaderBoardScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    //TODO(not done because i need to talk with teacher first to know how to do this test)

    @Test
    fun try_to_click_on_burger_menu_and_check_if_it_opens() {
        //Arrange
        composeTestRule.setContent {
            val nPlayers = 200
            val playersRankingInfo = Leaderboard.generateRankingInfo(nPlayers)
            LeaderboardScreen(
                inDarkTheme = false,
                listLeaderboard = Loaded(Result.success(emptyList())),
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
            val nPlayers = 200
            val playersRankingInfo = Leaderboard.generateRankingInfo(nPlayers)
            LeaderboardScreen(
                inDarkTheme = false,
                listLeaderboard = Loaded(Result.success(emptyList())),
                setDarkTheme = {},
                toFindGameScreen = { findGameWasCalled = true },
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
            val nPlayers = 200
            val playersRankingInfo = Leaderboard.generateRankingInfo(nPlayers)
            LeaderboardScreen(
                inDarkTheme = false,
                listLeaderboard = Loaded(Result.success(emptyList())),
                setDarkTheme = {},
                toFindGameScreen = {},
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
            val nPlayers = 200
            val playersRankingInfo = Leaderboard.generateRankingInfo(nPlayers)
            LeaderboardScreen(
                inDarkTheme = false,
                listLeaderboard = Loaded(Result.success(emptyList())),
                setDarkTheme = {},
                toFindGameScreen = {},
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
            val nPlayers = 200
            val playersRankingInfo = Leaderboard.generateRankingInfo(nPlayers)
            LeaderboardScreen(
                inDarkTheme = false,
                Loaded(Result.success(emptyList())),
                setDarkTheme = {},
                toFindGameScreen = {},
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