package gomoku.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gomoku.login.domain.AuthValidator
import gomoku.login.domain.InputTextFieldData
import gomoku.login.domain.Login
import gomoku.login.domain.Password
import gomoku.login.domain.Username
import gomoku.shared.components.Form
import gomoku.shared.components.HyperLink
import gomoku.shared.components.InputTextField
import gomoku.shared.components.SubmitButton
import pdm.gomoku.R


// Config
private val signUpSpacerWidth = 1.dp
private val signUpSpacerHeight = 10.dp

/**
 * Represents the login screen main composable.
 * @param screenState indicates the screen state.
 * @param onSubmit callback to be executed when the submit button is clicked.
 * @param onSignUpLinkClick callback to be executed when the sign-up link is clicked.
 */
@Composable
fun LoginView(
    screenState: LoginScreenState,
    onSubmit: (username: String, password: String) -> Unit,
    onSignUpLinkClick: (Int) -> Unit = {}
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    Form(
        title = stringResource(Login.formTitle),
        inputFieldsData = listOf(
            InputTextFieldData(
                value = username,
                label = stringResource(Login.usernameLabel),
                iconId = R.drawable.user,
                onValueChangeCallback = { username = it },
                validationCallback = { Username.isValid(it) },
                supportingText = stringResource(
                    id = Username.validationRuleResourceId,
                    Username.minUsernameLength,
                    Username.maxUsernameLength
                ),
            ),
            InputTextFieldData(
                value = password,
                label = stringResource(Login.passwordLabel),
                iconId = R.drawable.lock,
                onValueChangeCallback = { password = it },
                validationCallback = { Password.isValid(it) },
                supportingText = stringResource(
                    id = Password.validationRuleResourceId,
                    Password.minPasswordLength,
                    Password.maxPasswordLength
                ),
                isPassword = true
            )
        ),
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
        },
        footer = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (screenState.isLoading()) {
                    CircularProgressIndicator()
                } else {
                    SubmitButton(
                        enable = AuthValidator.areLoginCredentialsValid(username, password),
                        onButtonText = stringResource(Login.submitButtonText),
                        onClick = {
                            onSubmit(username, password)
                        }
                    )
                    Spacer(modifier = Modifier.height(signUpSpacerHeight))
                    if (screenState.isFailedLogin()) {
                        Text(
                            text = "Invalid credentials",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Spacer(modifier = Modifier.height(signUpSpacerHeight))
                    SignUpLink(onSignUpLinkClick)
                }
            }
        }
    )

}

/**
 * Reminds the user that he can sign up.
 */
@Composable
private fun SignUpLink(onSignUpLinkClick: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Login.noAccountMessage),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.inversePrimary,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(signUpSpacerWidth))
        HyperLink(
            text = stringResource(Login.signUpLinkText),
            onClick = onSignUpLinkClick
        )
    }
}