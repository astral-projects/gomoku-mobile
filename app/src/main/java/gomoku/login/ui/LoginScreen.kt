package gomoku.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Text
import gomoku.login.domain.AuthValidator
import gomoku.login.domain.InputTextFieldData
import gomoku.login.domain.Login
import gomoku.login.domain.Password
import gomoku.login.domain.Username
import gomoku.login.ui.components.HeaderLogo
import gomoku.ui.background.Background
import gomoku.ui.components.Form
import gomoku.ui.components.HyperLink
import gomoku.ui.components.InputTextField
import gomoku.ui.components.SubmitButton
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R

// Config
private val signUpSpacerWidth = 1.dp
private val signUpSpacerHeight = 10.dp

/**
 * Represents the login screen main composable.
 * @param onSubmit callback to be executed when the submit button is clicked.
 * @param onSignUpLinkClick callback to be executed when the sign-up link is clicked.
 */
@Composable
fun LoginScreen(
    onSubmit: (username: String, password: String) -> Unit = { _, _ -> },
    onSignUpLinkClick: (Int) -> Unit = {}
) {
    GomokuTheme {
        Background(
            header = { HeaderLogo() },
        ) {
            var username by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }
            Form(
                title = Login.FORM_TITLE,
                inputFieldsData = listOf(
                    InputTextFieldData(
                        value = username,
                        label = Login.USERNAME_LABEL,
                        iconId = R.drawable.user,
                        onValueChangeCallback = { username = it },
                        validationCallback = { Username.isValid(it) },
                        supportingText = Username.validationRuleMsg()
                    ),
                    InputTextFieldData(
                        value = password,
                        label = Login.PASSWORD_LABEL,
                        iconId = R.drawable.lock,
                        onValueChangeCallback = { password = it },
                        validationCallback = { Password.isValid(it) },
                        supportingText = Password.validationRuleMsg(),
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
                        SubmitButton(
                            enable = AuthValidator.areLoginCredentialsValid(username, password),
                            onButtonText = Login.SUBMIT_BUTTON_TEXT,
                            onClick = {
                                onSubmit(username, password)
                            }
                        )
                        Spacer(modifier = Modifier.height(signUpSpacerHeight))
                        SignUpLink(onSignUpLinkClick)
                    }
                }
            )
        }
    }
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
            text = Login.NO_ACCOUNT_MESSAGE,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.inversePrimary,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(signUpSpacerWidth))
        HyperLink(
            text = Login.SIGN_UP_LINK,
            onClick = onSignUpLinkClick
        )
    }
}

@Composable
@Preview
private fun LoginScreenPreview() {
    LoginScreen()
}
