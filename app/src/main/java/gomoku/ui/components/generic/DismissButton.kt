package gomoku.ui.components.generic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A generic close button for dismissing content like dialogs or to confirm and close action.
 * @param onButtonText Text to be placed in the button.
 * @param onDismiss Callback function to be executed when the dismiss button is clicked.
 * @param enable Indicates whether the button should be enabled.
 */
@Composable
fun DismissButton(
    onButtonText: String,
    onDismiss: () -> Unit,
    backgroundColor: Color=MaterialTheme.colorScheme.error,
    letterColor: Color=Color.White,
    enable: Boolean = true
) {
    Button(
        onClick = onDismiss,
        enabled = enable,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = letterColor
        ),
        border = BorderStroke(3.dp, MaterialTheme.colorScheme.errorContainer),
        shape = RoundedCornerShape(10.dp)
    ) { Text(text = onButtonText, fontWeight = FontWeight.Bold) }
}


@Preview
@Composable
private fun TestDismissButton() {
    DismissButton(
        onButtonText = "Some text",
        enable = true,
        onDismiss = {}
    )
}