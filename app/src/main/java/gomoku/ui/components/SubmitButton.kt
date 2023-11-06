package gomoku.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.background.BackgroundConfig

// Config
private val buttonBorderWidth = 2.dp
private val roundedCornerShapeSize = 10.dp
private const val BUTTON_WIDTH_FACTOR = 0.7f
private const val BUTTON_HEIGHT_FACTOR = 0.05f

/**
 * A generic button for submitting content.
 * @param modifier Modifier to be applied to the button.
 * @param onButtonText Text to be placed in the button.
 * @param backgroundConfig Configuration for the background.
 * @param onClick Callback function to be executed when the button is clicked.
 * @param backgroundColor Color of the button background.
 * @param textColor Color of the button text.
 * @param enable Indicates whether the button should be enabled.
 */
@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    onButtonText: String,
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onClick: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    enable: Boolean = true
) {
    val boxWidth = backgroundConfig.screenWidth * BUTTON_WIDTH_FACTOR
    val boxHeight = backgroundConfig.screenHeight * BUTTON_HEIGHT_FACTOR
    Button(
        modifier = modifier
            .width(boxWidth)
            .height(boxHeight)
            .testTag(onButtonText),
        onClick = onClick,
        enabled = enable,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        border = BorderStroke(
            width = buttonBorderWidth,
            color = MaterialTheme.colorScheme.outline
        ),
        shape = RoundedCornerShape(roundedCornerShapeSize),
    ) {
        Text(
            text = onButtonText,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun SubmitButtonPreview() {
    SubmitButton(
        onButtonText = "Login",
        textColor = Color.Black
    )
}