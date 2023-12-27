package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.annotation.ExperimentalTestApi
import gomoku.domain.about.About
import gomoku.ui.about.AboutScreen
import gomoku.ui.shared.components.AboutTheDeveloperLabel
import gomoku.ui.shared.components.BurgerMenuButton
import gomoku.ui.shared.components.FeedbackAndSupportLabel
import gomoku.ui.shared.components.GameInformationLabel
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuButtonClose
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuFindGameButton
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuLeaderboardButton
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuLogoutButton
import gomoku.ui.shared.components.navigationDrawer.BurgerMenuSwitchThemeButton
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalTestApi
class AboutScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun click_on_game_information_expands_the_Card() {
        // Arrange
        composeTestRule.setContent {
            AboutScreen(
                sections = About.sections,
                toFindGameScreen = {},
                toLeaderboardScreen = {},
                onLogoutRequest = {}
            )
        }
        // Act
        composeTestRule.onNodeWithTag(GameInformationLabel).performClick()
    }

    @Test
    fun click_on_feedback_and_support_expands_the_Card() {
        // Arrange
        composeTestRule.setContent {
            AboutScreen(
                sections = About.sections,
                toFindGameScreen = {},
                toLeaderboardScreen = {},
                onLogoutRequest = {}
            )
        }
        // Act
        composeTestRule.onNodeWithTag(FeedbackAndSupportLabel).performClick()
    }

    @Test
    fun click_on_about_the_developer_expands_the_Card() {
        // Arrange
        composeTestRule.setContent {
            AboutScreen(
                sections = About.sections,
                toFindGameScreen = {},
                toLeaderboardScreen = {},
                onLogoutRequest = {}
            )
        }
        // Act
        composeTestRule.onNodeWithTag(AboutTheDeveloperLabel).performClick()
    }

    @Test
    fun trying_to_click_on_burger_menu() {
        // Arrange
        composeTestRule.setContent {
            AboutScreen(
                sections = About.sections,
                toFindGameScreen = {},
                toLeaderboardScreen = {},
                onLogoutRequest = {}
            )
        }
        // Act

        composeTestRule.onNodeWithTag(BurgerMenuButton).performClick()
        composeTestRule.onNodeWithTag(BurgerMenuButton).assertExists()
    }

    @Test
    fun trying_to_close_on_burger_menu() {
        // Arrange
        composeTestRule.setContent {
            AboutScreen(
                sections = About.sections,
                toFindGameScreen = {},
                toLeaderboardScreen = {},
                onLogoutRequest = {}
            )
        }
        // Act
        composeTestRule.onNodeWithTag(BurgerMenuButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuButton).performClick()

        // Assert
        composeTestRule.onNodeWithTag(BurgerMenuButtonClose).performClick()
        composeTestRule.onNodeWithTag(BurgerMenuButtonClose).assertExists()

    }

    @Test
    fun on_burger_menu_try_to_navigate_to_logout() {
        var logoutWasCalled = false
        // Arrange
        composeTestRule.setContent {
            AboutScreen(
                sections = About.sections,
                toFindGameScreen = {},
                toLeaderboardScreen = {},
                onLogoutRequest = {
                    logoutWasCalled = true
                }
            )
        }
        // Act
        composeTestRule.onNodeWithTag(BurgerMenuLogoutButton).performClick()

        // Assert
        assertTrue(logoutWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_Find_Game_Screen() {
        var findGameWasCalled = false
        // Arrange
        composeTestRule.setContent {
            AboutScreen(
                sections = About.sections,
                toFindGameScreen = {
                    findGameWasCalled = true
                },
                toLeaderboardScreen = {},
                onLogoutRequest = {}
            )
        }
        // Act
        composeTestRule.onNodeWithTag(BurgerMenuFindGameButton).performClick()

        // Assert
        assertTrue(findGameWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_leaderboard() {
        var leaderboardWasCalled = false
        // Arrange
        composeTestRule.setContent {
            AboutScreen(
                sections = About.sections,
                toFindGameScreen = {},
                toLeaderboardScreen = {
                    leaderboardWasCalled = true
                },
                onLogoutRequest = {}
            )
        }
        // Act
        composeTestRule.onNodeWithTag(BurgerMenuLeaderboardButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuLeaderboardButton).performClick()

        // Assert
        assertTrue(leaderboardWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_switch_theme() {
        // Arrange
        composeTestRule.setContent {
            AboutScreen(
                sections = About.sections,
                toFindGameScreen = {},
                toLeaderboardScreen = {},
                onLogoutRequest = {}
            )
        }
        // Act
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).performClick()

        // Assert
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).assertExists()
    }


}