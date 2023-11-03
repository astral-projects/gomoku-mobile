package gomoku.ui.components.generic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pdm.gomoku.R

private val iconPadding = 30.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldWithValidation(
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    iconId: Int = R.drawable.user,
    colors : TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        disabledBorderColor = Color.Transparent,
        errorBorderColor = Color.Transparent
    ) ,
    validateField: (text: String) -> Boolean = { text -> text.length >=1 },
    searchField : (text: String) -> Unit = {},
): Pair<String, Boolean> {
    var name by remember { mutableStateOf("") }
    var invalidField by remember { mutableStateOf(!validateField(name)) }
    OutlinedTextField(
        value = name,
        onValueChange = {
            name = it
            invalidField = !validateField(name)
            searchField(it)
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier
                    .size(iconPadding)
            )

        },
        modifier =modifier,
        placeholder = { Text(
            text = placeholderText,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1
        ) },
        singleLine = true,
        trailingIcon = {
            if (name.isNotEmpty()) {
                if (invalidField)
                    Icon(
                        Icons.Filled.Clear,
                        "Invalid Input",
                    )
                else
                    Icon(
                        Icons.Filled.Done,
                        "Valid Input"
                    )
            }
        },
        isError = invalidField,
        colors = colors
    )
    return name to invalidField
}

@Preview
@Composable
private fun TestOutlinedTextFieldWithValidation() {
    fun validateField(text: String): Boolean = text.length > 2
    OutlinedTextFieldWithValidation(
        placeholderText = "other input",
        validateField = ::validateField,
    )
}