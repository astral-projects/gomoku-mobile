package gomoku.ui.register.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import gomoku.domain.login.AuthValidator
import gomoku.domain.login.InputTextFieldData
import gomoku.domain.login.Password
import gomoku.domain.login.Username
import gomoku.domain.register.Email
import gomoku.domain.register.Register
import gomoku.ui.register.RegisterScreenState
import gomoku.ui.register.isFail
import gomoku.ui.register.isLoading
import gomoku.ui.shared.components.Form
import gomoku.ui.shared.components.InputTextField
import gomoku.ui.shared.components.SubmitButton
import pdm.gomoku.R

// Config
private val paddingBetweenInputFields = 5.dp
private val footerPaddingTop = 5.dp
private val footerPaddingBottom = 10.dp

/**
 * Represents the register screen main composable.
 * @param registerScreenState the screen state.
 * @param onCreateUser callback to be executed when the submit button is clicked.
 */
@Composable
fun RegisterView(
    registerScreenState: RegisterScreenState,
    onCreateUser: (username: String, email: String, password: String) -> Unit,
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
            //Todo: I dont like the column here but I dont know how to do it better
            Column {
                if (registerScreenState.isLoading()) {
                    CircularProgressIndicator()
                } else {
                    SubmitButton(
                        enable = AuthValidator.areRegistrationCredentialsValid(
                            username,
                            email,
                            password,
                            confirmPassword
                        ) && !registerScreenState.isLoading(),
                        onButtonText = stringResource(Register.submitTextButton),
                        onClick = {
                            onCreateUser(
                                username,
                                email,
                                password
                            )
                        },
                        textColor = MaterialTheme.colorScheme.outline,
                    )
                    if (registerScreenState.isFail()) {
                        Text(
                            text = "Invalid credentials",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
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