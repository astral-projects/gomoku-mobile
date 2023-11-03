package gomoku.ui.components.generic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val borderWidth = 2.dp
private val roundedCornerShapeSize = 10.dp

/**
 * A generic close button for dismissing content in a popup or a dialog.
 * @param modifier Modifier to be applied to the button.
 * @param onButtonText Text to be placed in the button.
 * @param onClick Callback function to be executed when the button is clicked.
 * @param backgroundColor Color of the button background.
 * @param textColor Color of the button text.
 * @param enable Indicates whether the button should be enabled.
 */
@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    onButtonText: String,
    onClick: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    enable: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enable,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        border = BorderStroke(
            width = borderWidth,
            color = MaterialTheme.colorScheme.outline
        ),
        modifier = modifier,
        shape = RoundedCornerShape(roundedCornerShapeSize),
    ) { Text(text = onButtonText, fontWeight = FontWeight.Bold) }
}

@Preview
@Composable
private fun SubmitButtonPreview() {
    SubmitButton(
        onButtonText = "Login",
        textColor = Color.Black
    )
}