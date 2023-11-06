package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import gomoku.home.ui.HomeScreen
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun click_on_find_match_navigation_calls_onFindMatch() {
        // Arrange
        var findMatchRequested = false
        var leaderBoardRequested = false
        composeTestRule.setContent {
            HomeScreen(
                username = "test",
                onFindMatch = {
                    findMatchRequested = true
                },
                onLeaderBoard = {
                    leaderBoardRequested = true
                },
            )
        }
        // Act
        composeTestRule.onNodeWithTag("Find a Match").performClick()
        // Assert
        assertTrue(findMatchRequested)
        assertFalse(leaderBoardRequested)
    }

    @Test
    fun click_on_leaderboard_navigation_calls_onLeaderBoard() {
        // Arrange
        var leaderBoardRequested = false
        composeTestRule.setContent {
            HomeScreen(
                username = "test",
                onLeaderBoard = {
                    leaderBoardRequested = true
                },
            )
        }
        // Act
        composeTestRule.onNodeWithTag("Leaderboard").performClick()
        // Assert
        assertTrue(leaderBoardRequested)
    }

    @Test
    fun click_on_about_navigation_calls_onAbout() {
        // Arrange
        var onAboutRequested = false
        composeTestRule.setContent {
            HomeScreen(
                username = "test",
                onAbout = {
                    onAboutRequested = true
                },

            )
        }
        // Act
        composeTestRule.onNodeWithTag("About").performClick()
        // Assert
        assertTrue(onAboutRequested)
    }

    @Test
    fun click_on_logout_navigation_calls_onLogout(){
        //Arrange
        var onLogoutRequested = false
        composeTestRule.setContent {
            HomeScreen(
                username = "test",
                onLogout = {
                    onLogoutRequested= true
                })
        }
        //Act
        composeTestRule.onNodeWithTag("Logout").performClick()
        //Assert
        assertTrue(onLogoutRequested)

    }


}