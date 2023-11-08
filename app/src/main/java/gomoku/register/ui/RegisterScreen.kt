package gomoku.register.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.home.domain.Home.gameName
import gomoku.login.domain.AuthValidator
import gomoku.login.domain.InputTextFieldData
import gomoku.login.domain.Password
import gomoku.login.domain.Username
import gomoku.register.domain.Email
import gomoku.register.domain.Register
import gomoku.register.ui.components.FooterBubbles
import gomoku.shared.background.Background
import gomoku.shared.components.Form
import gomoku.shared.components.HeadlineText
import gomoku.shared.components.InputTextField
import gomoku.shared.components.SubmitButton
import gomoku.shared.theme.GomokuTheme
import pdm.gomoku.R

// Config
private val paddingBetweenInputFields = 5.dp
private val footerPaddingTop = 5.dp
private val footerPaddingBottom = 10.dp

/**
 * Represents the register screen main composable.
 * @param onSubmit callback to be executed when the submit button is clicked.
 */
@Composable
fun RegisterScreen(
    onSubmit: (username: String, email: String, password: String, confirmPassword: String) -> Unit
) {
    GomokuTheme {
        Background(
            header = { HeadlineText(text = stringResource(gameName)) },
            footer = { FooterBubbles() }
        ) {
            var username by rememberSaveable { mutableStateOf("") }
            var email by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }
            var confirmPassword by rememberSaveable { mutableStateOf("") }
            Form(
                footerPaddingTop = footerPaddingTop,
                footerPaddingBottom = footerPaddingBottom,
                paddingBetweenInputFields = paddingBetweenInputFields,
                title = stringResource(Register.formTitle),
                inputFieldsData = listOf(
                    InputTextFieldData(
                        value = username,
                        label = stringResource(Register.usernameLabel),
                        iconId = R.drawable.user,
                        onValueChangeCallback = { username = it },
                        validationCallback = { Username.isValid(it) },
                        supportingText = stringResource(
                            id = Username.validationRuleResourceId,
                            Username.minUsernameLength,
                            Username.maxUsernameLength
                        )
                    ),
                    InputTextFieldData(
                        value = email,
                        label = stringResource(Register.emailLabel),
                        iconId = R.drawable.email,
                        onValueChangeCallback = { email = it },
                        validationCallback = { Email.isValid(it) },
                        supportingText = stringResource(id = Email.validationRuleResourceId)
                    ),
                    InputTextFieldData(
                        value = password,
                        label = stringResource(Register.passwordLabel),
                        iconId = R.drawable.lock,
                        onValueChangeCallback = { password = it },
                        validationCallback = { Password.isValid(it) },
                        isPassword = true,
                        supportingText = stringResource(
                            id = Password.validationRuleResourceId,
                            Password.minPasswordLength,
                            Password.maxPasswordLength
                        )
                    ),
                    InputTextFieldData(
                        value = confirmPassword,
                        label = stringResource(Register.confirmPasswordLabel),
                        iconId = R.drawable.lock,
                        onValueChangeCallback = { confirmPassword = it },
                        validationCallback = { Password.isValid(it) },
                        isPassword = true
                    )
                ),
                footer = {
                    SubmitButton(
                        enable = AuthValidator.areRegistrationCredentialsValid(
                            username,
                            email,
                            password,
                            confirmPassword
                        ),
                        onButtonText = stringResource(Register.submitTextButton),
                        onClick = { onSubmit(username, email, password, confirmPassword) },
                        textColor = MaterialTheme.colorScheme.outline,
                    )
                },
                renderInputField = { inputFieldData ->
                    InputTextField(
                        value = inputFieldData.value,
                        label = inputFieldData.label,
                        onValueChange = inputFieldData.onValueChangeCallback,
                        validateField = inputFieldData.validationCallback,
                        leadingIconId = inputFieldData.iconId,
                        supportingText = inputFieldData.supportingText,
                        enablePasswordVisibility = inputFieldData.isPassword
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(
        onSubmit = { _, _, _, _ -> }
    )
}