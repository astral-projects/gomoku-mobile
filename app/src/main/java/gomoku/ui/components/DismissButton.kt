package gomoku.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A generic close button for dismissing content like dialogs.
 * @param onButtonText Text to be placed in the button.
 * @param enable Indicates whether the button should be enabled.
 * @param onDismiss Callback function to be executed when the dismiss button is clicked.
 */
@Composable
fun DismissButton(
    onButtonText: String,
    enable: Boolean,
    onDismiss: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier.testTag(onButtonText),
        onClick = onDismiss,
        enabled = enable,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = Color.White
        ),
        border = BorderStroke(
            width = 3.dp,
            color = MaterialTheme.colorScheme.errorContainer
        ),
        shape = RoundedCornerShape(10.dp)
    ) { Text(onButtonText) }
}

@Preview
@Composable
private fun TestDismissButton() {
    DismissButton(
        onButtonText = "Is this a test?",
        enable = true,
        onDismiss = {}
    )
}