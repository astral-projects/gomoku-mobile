package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import gomoku.register.domain.Register
import gomoku.register.ui.RegisterScreen
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class RegisterScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun trying_to_click_on_the_navigation_register_without_filling_the_params_fields() {
        // Arrange
        var registerRequested = false

        composeTestRule.setContent {
            RegisterScreen(
                onSubmit = { _,_,_,_ ->
                    registerRequested = true
                },
            )
        }
        // Act
        composeTestRule.onNodeWithTag(Register.SUBMIT_BUTTON_TEXT).performClick()
        assertFalse(registerRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_register_without_filling_the_username_field() {
        //Arrange
        var registerRequested = false
        val email = "john@gamil.com"
        val password = "testingfunction"
        val confirmPassword = "testingfunction"
        composeTestRule.setContent {
            RegisterScreen(
                onSubmit = { _, _, _, _ ->
                    registerRequested = true
                },
            )
        }
        // Act
        composeTestRule.onNodeWithTag(Register.EMAIL_LABEL).performTextInput(email)
        composeTestRule.onNodeWithTag(Register.PASSWORD_LABEL).performTextInput(password)
        composeTestRule.onNodeWithTag(Register.CONFIRM_PASSWORD_LABEL).performTextInput(confirmPassword)
        composeTestRule.onNodeWithTag(Register.SUBMIT_BUTTON_TEXT).performClick()
        // Assert
        assertFalse(registerRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_register_without_filling_the_email_field() {
        //Arrange
        var registerRequested = false
        val username = "Username1"
        val email = "john@gamil.com"
        val password = "testingfunction"
        val confirmPassword = "testingfunction"
        composeTestRule.setContent {
            RegisterScreen(
                onSubmit = { _, _, _, _ ->
                    registerRequested = true
                },
            )
        }
        // Act
        composeTestRule.onNodeWithTag(Register.USERNAME_LABEL).performTextInput(username)
        composeTestRule.onNodeWithTag(Register.PASSWORD_LABEL).performTextInput(password)
        composeTestRule.onNodeWithTag(Register.CONFIRM_PASSWORD_LABEL).performTextInput(confirmPassword)
        composeTestRule.onNodeWithTag(Register.SUBMIT_BUTTON_TEXT).performClick()
        // Assert
        assertFalse(registerRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_register_without_filling_the_password_field() {
        //Arrange
        var registerRequested = false
        val username = "Username1"
        val email = "john@gamil.com"
        val password = "testingfunction"
        val confirmPassword = "testingfunction"
        composeTestRule.setContent {
            RegisterScreen(
                onSubmit = { _, _, _, _ ->
                    registerRequested = true
                },
            )
        }
        // Act
        composeTestRule.onNodeWithTag(Register.USERNAME_LABEL).performTextInput(username)
        composeTestRule.onNodeWithTag(Register.EMAIL_LABEL).performTextInput(email)
        composeTestRule.onNodeWithTag(Register.CONFIRM_PASSWORD_LABEL).performTextInput(confirmPassword)
        composeTestRule.onNodeWithTag(Register.SUBMIT_BUTTON_TEXT).performClick()
        // Assert
        assertFalse(registerRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_register_without_filling_the_confirm_password_field() {
        //Arrange
        var registerRequested = false
        val username = "Username1"
        val email = "john@gamil.com"
        val password = "testingfunction"
        composeTestRule.setContent {
            RegisterScreen(
                onSubmit = { _, _, _, _ ->
                    registerRequested = true
                },
            )
        }
        // Act
        composeTestRule.onNodeWithTag(Register.USERNAME_LABEL).performTextInput(username)
        composeTestRule.onNodeWithTag(Register.EMAIL_LABEL).performTextInput(email)
        composeTestRule.onNodeWithTag(Register.PASSWORD_LABEL).performTextInput(password)
        composeTestRule.onNodeWithTag(Register.SUBMIT_BUTTON_TEXT).performClick()
        // Assert
        assertFalse(registerRequested)
    }

    @Test
    fun click_on_register_navigation_calls_onRegister() {
        //Arrange
        var registerRequested = false
        val username = "Username1"
        val email = "john@gamil.com"
        val password = "testingfunction"
        val confirmPassword = "testingfunction"
        composeTestRule.setContent {
            RegisterScreen(
                onSubmit = { _, _, _, _ ->
                    registerRequested = true
                },
            )
        }
        // Act
        composeTestRule.onNodeWithTag(Register.USERNAME_LABEL).performTextInput(username)
        composeTestRule.onNodeWithTag(Register.EMAIL_LABEL).performTextInput(email)
        composeTestRule.onNodeWithTag(Register.PASSWORD_LABEL).performTextInput(password)
        composeTestRule.onNodeWithTag(Register.CONFIRM_PASSWORD_LABEL).performTextInput(confirmPassword)
        composeTestRule.onNodeWithTag(Register.SUBMIT_BUTTON_TEXT).performClick()
        // Assert
        assertTrue(registerRequested)
    }

}