package gomoku.screens.mainscreen

const val RegisterSubmitLabelButton = "Register"
const val RegisterUserNameLabel = "Username"
const val RegisterEmailLabel = "Email"
const val RegisterPasswordLabel = "Password"
const val RegisterConfirmPasswordLabel = "Confirm Password"

class RegisterScreenTests {
    /*
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun trying_to_click_on_the_navigation_register_without_filling_the_params_fields() {
        // Arrange
        var registerRequested = false

        composeTestRule.setContent {
            RegisterScreen { _, _, _, _ ->
                registerRequested = true
            }
        }
        // Act
        composeTestRule.onNodeWithTag(RegisterSubmitLabelButton).performClick()
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
            RegisterScreen { _, _, _, _ ->
                registerRequested = true
            }
        }
        // Act
        composeTestRule.onNodeWithTag(RegisterEmailLabel).performTextInput(email)
        composeTestRule.onNodeWithTag(RegisterPasswordLabel).performTextInput(password)
        composeTestRule.onNodeWithTag(RegisterConfirmPasswordLabel)
            .performTextInput(confirmPassword)
        composeTestRule.onNodeWithTag(RegisterSubmitLabelButton).performClick()
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
            RegisterScreen { _, _, _, _ ->
                registerRequested = true
            }
        }
        // Act
        composeTestRule.onNodeWithTag(RegisterUserNameLabel).performTextInput(username)
        composeTestRule.onNodeWithTag(RegisterPasswordLabel).performTextInput(password)
        composeTestRule.onNodeWithTag(RegisterConfirmPasswordLabel)
            .performTextInput(confirmPassword)
        composeTestRule.onNodeWithTag(RegisterSubmitLabelButton).performClick()
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
            RegisterScreen { _, _, _, _ ->
                registerRequested = true
            }
        }
        // Act
        composeTestRule.onNodeWithTag(RegisterUserNameLabel).performTextInput(username)
        composeTestRule.onNodeWithTag(RegisterEmailLabel).performTextInput(email)
        composeTestRule.onNodeWithTag(RegisterConfirmPasswordLabel)
            .performTextInput(confirmPassword)
        composeTestRule.onNodeWithTag(RegisterSubmitLabelButton).performClick()
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
            RegisterScreen { _, _, _, _ ->
                registerRequested = true
            }
        }
        // Act
        composeTestRule.onNodeWithTag(RegisterUserNameLabel).performTextInput(username)
        composeTestRule.onNodeWithTag(RegisterEmailLabel).performTextInput(email)
        composeTestRule.onNodeWithTag(RegisterPasswordLabel).performTextInput(password)
        composeTestRule.onNodeWithTag(RegisterSubmitLabelButton).performClick()
        // Assert
        assertFalse(registerRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_register_calls_onRegister() {
        //Arrange
        var registerRequested = false
        val username = "Username1"
        val email = "john@gamil.com"
        val password = "testingfunction"
        val confirmPassword = "testingfunction"
        composeTestRule.setContent {
            RegisterScreen { _, _, _, _ ->
                registerRequested = true
            }
        }
        // Act
        composeTestRule.onNodeWithTag(RegisterUserNameLabel).performTextInput(username)
        composeTestRule.onNodeWithTag(RegisterEmailLabel).performTextInput(email)
        composeTestRule.onNodeWithTag(RegisterPasswordLabel).performTextInput(password)
        composeTestRule.onNodeWithTag(RegisterConfirmPasswordLabel)
            .performTextInput(confirmPassword)
        composeTestRule.onNodeWithTag(RegisterSubmitLabelButton).performClick()
        // Assert
        assertTrue(registerRequested)
    }

    @Test
    fun input_for_username_exists() {
        //Arrange
        composeTestRule.setContent {
            RegisterScreen { _, _, _, _ ->
            }
        }
        //Act
        composeTestRule.onNodeWithTag(RegisterUserNameLabel).assertExists()
    }

    @Test
    fun input_for_email_exists() {
        //Arrange
        composeTestRule.setContent {
            RegisterScreen { _, _, _, _ ->
            }
        }
        //Act
        composeTestRule.onNodeWithTag(RegisterEmailLabel).assertExists()
    }

    @Test
    fun input_for_password_exists() {
        //Arrange
        composeTestRule.setContent {
            RegisterScreen { _, _, _, _ ->
            }
        }
        //Act
        composeTestRule.onNodeWithTag(RegisterPasswordLabel).assertExists()
    }

    @Test
    fun input_for_confirm_password_exists() {
        //Arrange
        composeTestRule.setContent {
            RegisterScreen { _, _, _, _ ->
            }
        }
        //Act
        composeTestRule.onNodeWithTag(RegisterConfirmPasswordLabel).assertExists()
    }*/
}