package gomoku.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pdm.gomoku.R

// Config
private val iconPadding = 30.dp
private const val UNFOCUSED_BORDER_COLOR_OPACITY = 0.1f
private const val UNFOCUSED_SUPPORTING_TEXT_COLOR_OPACITY = 0.3f


//TODO(The label on the function OutlinedTextFieldWithValidation can not be null , because its important to the test to not be null)
/**
 * Composes an outlined text field that includes a placeholder, text validation
 * and icons that change, according to the current text value.
 * @param modifier The modifier to be applied to the text field.
 * @param value The current value of the text field.
 * @param label The label of the text field.
 * @param placeholderText The placeholder text of the text field.
 * @param leadingIconId The id of the icon to be used as leading icon.
 * @param supportingText The supporting text of the text field.
 * @param enablePasswordVisibility Whether the password visibility is to be enabled.
 * @param colors The colors of the text field.
 * @param onValueChange The callback to be executed when the text field value changes.
 * @param validateField The validation function to be applied to the text field in order to
 * validate it.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldWithValidation(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    placeholderText: String = "",
    leadingIconId: Int? = null,
    supportingText: String? = null,
    enablePasswordVisibility: Boolean = false,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        containerColor = MaterialTheme.colorScheme.primary,
        focusedBorderColor = MaterialTheme.colorScheme.inversePrimary,
        unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary
            .copy(alpha = UNFOCUSED_BORDER_COLOR_OPACITY),
        cursorColor = MaterialTheme.colorScheme.inversePrimary,
        errorCursorColor = MaterialTheme.colorScheme.onError,
        errorLeadingIconColor = MaterialTheme.colorScheme.onError,
        errorTrailingIconColor = MaterialTheme.colorScheme.onError,
        errorBorderColor = MaterialTheme.colorScheme.inversePrimary,
        unfocusedSupportingTextColor = MaterialTheme.colorScheme.inversePrimary
           .copy(alpha = UNFOCUSED_SUPPORTING_TEXT_COLOR_OPACITY),
        errorSupportingTextColor = MaterialTheme.colorScheme.inversePrimary,
        focusedSupportingTextColor = MaterialTheme.colorScheme.inversePrimary
    ),
    onValueChange: (String) -> Unit = {},
    validateField: (text: String) -> Boolean = { true }
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    var invalidField by rememberSaveable { mutableStateOf(!validateField(value)) }
    OutlinedTextField(
        value = value,
        onValueChange = {
            invalidField = !validateField(value)
            onValueChange(it)
        },
        supportingText = supportingText?.let {
            {
                Text(
                    text = supportingText,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
        visualTransformation = if (enablePasswordVisibility && !passwordVisibility)
            PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = leadingIconId?.let {
            {
                Image(
                    painter = painterResource(id = leadingIconId),
                    contentDescription = "Leading icon",
                    modifier = Modifier.size(iconPadding)
                )
            }
        },
        label = label?.let {
            {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        modifier = modifier.testTag(label),
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
        },
        singleLine = true,
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (value.isNotEmpty()) {
                    if (enablePasswordVisibility) {
                        IconButton(
                            onClick = { passwordVisibility = !passwordVisibility }
                        ) {
                            Icon(
                                if (passwordVisibility) {
                                    Icons.Filled.Visibility
                                } else {
                                    Icons.Filled.VisibilityOff
                                },
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    } else {
                        if (invalidField) {
                            Icon(
                                Icons.Filled.Lock,
                                "Invalid Input",
                            )
                        } else {
                            Icon(
                                Icons.Filled.Done,
                                "Valid Input",
                            )
                        }
                    }
                }
            }
        },
        isError = invalidField,
        colors = colors
    )
}

@Preview
@Composable
private fun OutlinedTextFieldWithValidationPreview() {
    fun validateField(text: String): Boolean = text.length > 2
    OutlinedTextFieldWithValidation(
        value = "some input",
        placeholderText = "other input",
        validateField = ::validateField,
        label = "Input",
        leadingIconId = R.drawable.man,
        supportingText = "Supporting text"
    )
}

@Preview
@Composable
private fun OutlinedTextFieldWithValidationAndPasswordEnabledPreview() {
    fun validateField(text: String): Boolean = text.length > 2
    OutlinedTextFieldWithValidation(
        value = "some input",
        placeholderText = "other input",
        validateField = ::validateField,
        label = "Input",
        leadingIconId = R.drawable.man,
        supportingText = "Supporting text",
        enablePasswordVisibility = true
    )
}