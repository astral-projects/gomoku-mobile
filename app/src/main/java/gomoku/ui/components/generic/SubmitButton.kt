package gomoku.ui.components.generic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.background.BackgroundConfig

/**
 * A generic close button for dismissing content like dialogs or to confirm and close action.
 * @param onButtonText Text to be placed in the button.
 * @param onClick Callback function to be executed when the dismiss button is clicked.
 * @param enable Indicates whether the button should be enabled.
 */

private val borderWidth = 2.dp
private val roundedCornerShapeSize = 7.dp

@Composable
fun SubmitButton(
    onButtonText: String,
    onClick: () -> Unit,
    backgroundColor: Color=MaterialTheme.colorScheme.secondary,
    letterColor: Color=Color.White,
    enable: Boolean = true,
    modifier:Modifier=Modifier
) {
    Button(
        onClick = onClick,
        enabled = enable,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = letterColor
        ),
        border = BorderStroke(
            width = borderWidth,
            color = MaterialTheme.colorScheme.outline
        ),
        shape = RoundedCornerShape(roundedCornerShapeSize),
        modifier = modifier
    ) { Text(text = onButtonText, fontWeight = FontWeight.Bold) }
}


@Preview
@Composable
private fun TestSubmitButton() {
    val boxWidth = BackgroundConfig(LocalConfiguration.current).screenWidth * 0.6f
    val boxHeight = BackgroundConfig(LocalConfiguration.current).screenHeight * 0.04f
    SubmitButton(onButtonText = "Login",
        onClick = { /*TODO*/ },
        modifier = Modifier
            .height(boxHeight)
            .width(boxWidth),
        letterColor = Color.Black)
}