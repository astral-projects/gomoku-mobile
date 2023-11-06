package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.annotation.ExperimentalTestApi
import gomoku.about.domain.About
import gomoku.about.ui.AboutScreen
import gomoku.ui.components.test
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
                onBurgerMenuClick = { /*TODO*/ },
                sections = About.sections
            )
        }
        // Act
        composeTestRule.onNodeWithTag("Game Information$test").performClick()
    }

    @Test
    fun click_on_feedback_and_support_expands_the_Card() {
        // Arrange
        composeTestRule.setContent {
            AboutScreen(
                onBurgerMenuClick = { /*TODO*/ },
                sections = About.sections
            )
        }
        // Act
        composeTestRule.onNodeWithTag("Feedback and Support$test").performClick()
    }

    @Test
fun click_on_about_the_developer_expands_the_Card() {
        // Arrange
        composeTestRule.setContent {
            AboutScreen(
                onBurgerMenuClick = { /*TODO*/ },
                sections = About.sections
            )
        }
        // Act
        composeTestRule.onNodeWithTag("Authors$test").performClick()
    }
}