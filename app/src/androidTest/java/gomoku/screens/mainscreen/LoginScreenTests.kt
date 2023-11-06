package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import gomoku.login.ui.LoginScreen
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class LoginScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun click_on_login_navigation_calls_onLogin() {
        // Arrange
        var loginRequested = false
        val username = "Username1"
        val password = "testingfunction"

        composeTestRule.setContent {
            LoginScreen(
                onSubmit = { submittedUsername, submittedPassword ->
                    if (submittedUsername == username && submittedPassword == password) {
                        loginRequested = true
                    }
                }
                ,
            )
        }
        // Act
        composeTestRule.onNodeWithTag("Username").performTextInput(username)
        composeTestRule.onNodeWithTag("Password").performTextInput(password)
        composeTestRule.onNodeWithTag("Login").performClick()
        // Assert
        assertTrue(loginRequested)
    }
}