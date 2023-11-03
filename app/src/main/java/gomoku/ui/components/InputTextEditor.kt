package gomoku.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.components.generic.OutlinedTextFieldWithValidation
import gomoku.ui.theme.EggShell
import gomoku.ui.theme.Grey
import pdm.gomoku.R



/**
 * InputTextEditor Composable
 * @param text - The Text of the Input
 * @param iconId - The Image Logo to use in the image Composable
 * @param modifier - The Modifier Composable
 * @param image - The Image Composable. Receives the logo to use as parameter
 */
@Composable
fun InputTextEditor(
    modifier: Modifier = Modifier,
    text: String,
    iconId: Int,
    validateFunctionalInterface: (String) -> Boolean = { text -> text.length > 2},
) {
    OutlinedTextFieldWithValidation(
        modifier= modifier,
        validateField = validateFunctionalInterface,
        placeholderText = text,
        iconId = iconId,
    )
}

@Composable
@Preview(showBackground = true)
fun InputTextEditorPreview() {
    InputTextEditor(
        text = "User",
        iconId = R.drawable.user,
        modifier= Modifier
            .background(EggShell)
            .border(2.dp, Grey, RectangleShape),
    )
}
