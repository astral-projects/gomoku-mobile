package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import gomoku.login.domain.Login
import gomoku.login.ui.LoginScreen
import junit.framework.TestCase
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class LoginScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun trying_to_click_on_the_navigation_button_without_filling_the_username_and_password_fields() {
        // Arrange
        var loginRequested = false

        composeTestRule.setContent {
            LoginScreen(
                onSubmit = { submittedUsername, submittedPassword ->
                        loginRequested = true
                },
            )
        }
        // Act

        composeTestRule.onNodeWithTag(Login.SUBMIT_BUTTON_TEXT).performClick()
        // Assert
        assertFalse(loginRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_button_without_filling_the_username_field(){
        //Arrange
        var loginRequested = false
        val password = "testingfunction"

        composeTestRule.setContent {
            LoginScreen(
                onSubmit = { submittedUsername, submittedPassword ->
                        loginRequested = true
                },
            )
        }

        // Act
        composeTestRule.onNodeWithTag(Login.PASSWORD_LABEL).performTextInput(password)
        composeTestRule.onNodeWithTag(Login.SUBMIT_BUTTON_TEXT).performClick()
        // Assert
        assertFalse(loginRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_button_without_filling_the_password_field(){
        //Arrange
        var loginRequested = false
        val username = "Username1"

        composeTestRule.setContent {
            LoginScreen(
                onSubmit = { submittedUsername, submittedPassword ->
                        loginRequested = true
                },
            )
        }

        // Act
        composeTestRule.onNodeWithTag(Login.USERNAME_LABEL).performTextInput(username)
        composeTestRule.onNodeWithTag(Login.SUBMIT_BUTTON_TEXT).performClick()
        // Assert
        assertFalse(loginRequested)
    }

    @Test
    fun click_on_login_navigation_calls_onSubmit() {
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
                },
            )
        }
        // Act
        composeTestRule.onNodeWithTag(Login.USERNAME_LABEL).performTextInput(username)
        composeTestRule.onNodeWithTag(Login.PASSWORD_LABEL).performTextInput(password)
        composeTestRule.onNodeWithTag(Login.SUBMIT_BUTTON_TEXT).performClick()
        // Assert
        TestCase.assertTrue(loginRequested)
    }

    @Test
    fun putting_username_and_password_fields_correctly_without_clicking_login_button(){
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
                },
            )
        }
        // Act
        composeTestRule.onNodeWithTag(Login.USERNAME_LABEL).performTextInput(username)
        composeTestRule.onNodeWithTag(Login.PASSWORD_LABEL).performTextInput(password)
        // Assert
       assertFalse(loginRequested)
    }

    @Test
    fun click_on_SignUp_navigation_calls_onSignUpClick() {
        // Arrange
        var signUpRequested = false
        composeTestRule.setContent {
            LoginScreen(
                onSignUpLinkClick = {
                    signUpRequested = true
                },
            )
        }
        // Act
        composeTestRule.onNodeWithTag(Login.SIGN_UP_LINK).performClick()
        // Assert
        assertTrue(signUpRequested)
    }

    @Test
    fun trying_to_click_login_with_username_field_invalid(){
        // Arrange
        var loginRequested = false
        val username = "Username1dqwdqwdwqedefrqewiguhfnwertgjrwegvujewqgvnhorewigverwgvimejpvgjmwnergvoqe"
        val password = "testingfunction"

        composeTestRule.setContent {
            LoginScreen(
                onSubmit = { submittedUsername, submittedPassword ->
                        loginRequested = true
                },
            )
        }
        // Act
        composeTestRule.onNodeWithTag(Login.USERNAME_LABEL).performTextInput(username)
        composeTestRule.onNodeWithTag(Login.PASSWORD_LABEL).performTextInput(password)
        composeTestRule.onNodeWithTag(Login.SUBMIT_BUTTON_TEXT).performClick()
        // Assert
        assertFalse(loginRequested)
    }
}