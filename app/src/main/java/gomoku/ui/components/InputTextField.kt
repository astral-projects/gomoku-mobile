package gomoku.ui.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pdm.gomoku.R

/**
 * A [OutlinedTextField] with validation to be used by form input fields.
 * @param modifier The modifier to be applied to the input field.
 * @param value The value of the input field.
 * @param label The label of the input field.
 * @param leadingIconId The id of the icon to be used as leading icon.
 * @param onValueChange The callback to be executed when the input field value changes.
 * @param validateField The validation function to be applied to the input field.
 * @param supportingText The supporting text of the input field.
 * @param enablePasswordVisibility Whether the password visibility is to be enabled.
 */
@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    leadingIconId: Int,
    onValueChange: (String) -> Unit,
    validateField: (String) -> Boolean = { true },
    supportingText: String? = null,
    enablePasswordVisibility: Boolean = false,
) = OutlinedTextFieldWithValidation(
    value = value,
    modifier = modifier,
    label = label,
    leadingIconId = leadingIconId,
    onValueChange = onValueChange,
    validateField = validateField,
    supportingText = supportingText,
    enablePasswordVisibility = enablePasswordVisibility
)

@Composable
@Preview(showBackground = true)
private fun InputTextFieldPreview() {
    InputTextField(
        value = "Test",
        label = "User",
        onValueChange = {},
        leadingIconId = R.drawable.user
    )
}
