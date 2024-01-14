package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import gomoku.ui.login.LoginScreen
import gomoku.ui.login.LoginScreenState
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test


const val LoginSubmitButtonText = "Login"
const val LoginUsernameLabel = "Username"
const val LoginPasswordLabel = "Password"
const val LoginSignUpLinkText = "Sign up"

class LoginScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun trying_to_click_on_the_navigation_button_without_filling_the_username_and_password_fields() {
        // Arrange
        var loginRequested = false

        composeTestRule.setContent {
            LoginScreen(
                authenticatedUserInfo = LoginScreenState.Idle,
                onSignUpLinkClick = {},
                onSubmit = { submittedUsername, submittedPassword ->
                    loginRequested = true
                },
                onNoServerConnDialogDismiss = {},
            )
        }
        // Act

        composeTestRule.onNodeWithTag(LoginSubmitButtonText).performClick()
        // Assert
        assertFalse(loginRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_button_without_filling_the_username_field() {
        //Arrange
        var loginRequested = false
        val password = "testingfunction"

        composeTestRule.setContent {
            LoginScreen(
                authenticatedUserInfo = LoginScreenState.Idle,
                onSignUpLinkClick = {},
                onSubmit = { submittedUsername, submittedPassword ->
                    loginRequested = true
                },
                onNoServerConnDialogDismiss = {},
            )
        }

        // Act
        composeTestRule.onNodeWithTag(LoginPasswordLabel).performTextInput(password)
        composeTestRule.onNodeWithTag(LoginSubmitButtonText).performClick()
        // Assert
        assertFalse(loginRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_button_without_filling_the_password_field() {
        //Arrange
        var loginRequested = false
        val username = "Username1"

        composeTestRule.setContent {
            LoginScreen(
                authenticatedUserInfo = LoginScreenState.Idle,
                onSignUpLinkClick = {},
                onSubmit = { submittedUsername, submittedPassword ->
                    loginRequested = true
                },
                onNoServerConnDialogDismiss = {},
            )
        }

        // Act
        composeTestRule.onNodeWithTag(LoginUsernameLabel).performTextInput(username)
        composeTestRule.onNodeWithTag(LoginSubmitButtonText).performClick()
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
                authenticatedUserInfo = LoginScreenState.Idle,
                onSignUpLinkClick = {},
                onSubmit = { submittedUsername, submittedPassword ->
                    if (submittedUsername == username && submittedPassword == password) {
                        loginRequested = true
                    }
                },
                onNoServerConnDialogDismiss = {},
            )
        }
        // Act
        composeTestRule.onNodeWithTag(LoginUsernameLabel).performTextInput(username)
        composeTestRule.onNodeWithTag(LoginPasswordLabel).performTextInput(password)
        composeTestRule.onNodeWithTag(LoginSubmitButtonText).performClick()
        // Assert
        assertTrue(loginRequested)
    }

    @Test
    fun putting_username_and_password_fields_correctly_without_clicking_login_button() {
        // Arrange
        var loginRequested = false
        val username = "Username1"
        val password = "testingfunction"

        composeTestRule.setContent {
            LoginScreen(
                authenticatedUserInfo = LoginScreenState.Idle,
                onSignUpLinkClick = {},
                onSubmit = { submittedUsername, submittedPassword ->
                    if (submittedUsername == username && submittedPassword == password) {
                        loginRequested = true
                    }
                },
                onNoServerConnDialogDismiss = {},
            )
        }
        // Act
        composeTestRule.onNodeWithTag(LoginUsernameLabel).performTextInput(username)
        composeTestRule.onNodeWithTag(LoginPasswordLabel).performTextInput(password)
        // Assert
        assertFalse(loginRequested)
    }

    @Test
    fun click_on_SignUp_navigation_calls_onSignUpClick() {
        // Arrange
        var signUpRequested = false
        composeTestRule.setContent {
            LoginScreen(
                authenticatedUserInfo = LoginScreenState.Idle,
                onSubmit = { _, _ ->
                },
                onSignUpLinkClick = {
                    signUpRequested = true
                },
                onNoServerConnDialogDismiss = {},
            )
        }
        // Act
        composeTestRule.onNodeWithTag(LoginSignUpLinkText).performClick()
        // Assert
        assertTrue(signUpRequested)
    }

    @Test
    fun trying_to_click_login_with_username_field_invalid() {
        // Arrange
        var loginRequested = false
        val username =
            "Username1dqwdqwdwqedefrqewiguhfnwertgjrwegvujewqgvnhorewigverwgvimejpvgjmwnergvoqe"
        val password = "testingfunction"

        composeTestRule.setContent {
            LoginScreen(
                authenticatedUserInfo = LoginScreenState.Idle,
                onSignUpLinkClick = {},
                onSubmit = { submittedUsername, submittedPassword ->
                    loginRequested = true
                },
                onNoServerConnDialogDismiss = {},
            )
        }
        // Act
        composeTestRule.onNodeWithTag(LoginUsernameLabel).performTextInput(username)
        composeTestRule.onNodeWithTag(LoginPasswordLabel).performTextInput(password)
        composeTestRule.onNodeWithTag(LoginSubmitButtonText).performClick()
        // Assert
        assertFalse(loginRequested)
    }

    @Test
    fun trying_to_click_login_with_password_field_invalid() {
        // Arrange
        var loginRequested = false
        val username = "Username1"
        val password =
            "testingfunctiondqwdqwdwqedefrqewiguhfnwertgjrwegvujewqgvnhorewigverwgvimejpvgjmwnergvoqe"

        composeTestRule.setContent {
            LoginScreen(
                authenticatedUserInfo = LoginScreenState.Idle,
                onSignUpLinkClick = {},
                onSubmit = { submittedUsername, submittedPassword ->
                    loginRequested = true
                },
                onNoServerConnDialogDismiss = {},
            )
        }
        // Act
        composeTestRule.onNodeWithTag(LoginUsernameLabel).performTextInput(username)
        composeTestRule.onNodeWithTag(LoginPasswordLabel).performTextInput(password)
        composeTestRule.onNodeWithTag(LoginSubmitButtonText).performClick()
        // Assert
        assertFalse(loginRequested)
    }

    @Test
    fun input_for_username_exists() {
        // Arrange

        composeTestRule.setContent {
            LoginScreen(
                authenticatedUserInfo = LoginScreenState.Idle,
                onSignUpLinkClick = {},
                onSubmit = { _, _ ->
                },
                onNoServerConnDialogDismiss = {},
            )
        }
        // Act
        composeTestRule.onNodeWithTag(LoginUsernameLabel).assertExists()
        // Assert
    }

    @Test
    fun input_for_password_exists() {
        // Arrange

        composeTestRule.setContent {
            LoginScreen(
                authenticatedUserInfo = LoginScreenState.Idle,
                onSignUpLinkClick = {},
                onSubmit = { _, _ ->
                },
                onNoServerConnDialogDismiss = {},
            )
        }
        // Act
        composeTestRule.onNodeWithTag(LoginPasswordLabel).assertExists()
        // Assert
    }

    @Test
    fun input_for_login_button_exists() {
        // Arrange

        composeTestRule.setContent {
            LoginScreen(
                authenticatedUserInfo = LoginScreenState.Idle,
                onSignUpLinkClick = {},
                onSubmit = { _, _ ->
                },
                onNoServerConnDialogDismiss = {},
            )
        }
        // Act
        composeTestRule.onNodeWithTag(LoginSubmitButtonText).assertExists()
        // Assert
    }


}