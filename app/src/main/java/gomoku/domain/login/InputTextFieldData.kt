package gomoku.domain.login

/**
 * Represents the data and functionality associated with an input text field.
 * @param value The current value of the input field.
 * @param label The label of the input field.
 * @param iconId The id of the icon to be displayed in the input field.
 * @param onValueChangeCallback The callback to be executed when the input field value changes.
 * @param validationCallback The validation callback to be executed when the input field value changes.
 * The Field is always valid by default.
 * @param isPassword Whether the input field is a password field. Defaults to false.
 * Used to determine whether the password visibility is to be enabled.
 * @param supportingText The supporting text of the input field if any. Defaults to an empty string.
 */
data class InputTextFieldData(
    val value: String,
    val label: String,
    val iconId: Int,
    val onValueChangeCallback: (String) -> Unit,
    val validationCallback: (String) -> Boolean = { true },
    val isPassword: Boolean = false,
    val supportingText: String = "",
)
