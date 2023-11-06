package gomoku.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.theme.EggShell
import pdm.gomoku.R

// Config
private val borderWidth = 2.dp
private val roundedCornerShapeSize = 7.dp
private val iconSize = 40.dp
private val iconTextSpacerWidth = 8.dp
private const val BUTTON_WIDTH_FACTOR = 0.9f
private const val BUTTON_HEIGHT_FACTOR = 0.07f
const val NavigateBackTestTag = "NavigateBack"

/**
 * An [OutlinedButton] with an icon and text.
 * @param text The text to be displayed.
 * @param iconId The id of the icon to be displayed.
 * @param interiorColor The color of the interior of the button.
 * @param borderColor The color of the border of the button.
 * @param backgroundConfig The background configuration of the screen.
 * @param onClick The callback to be executed when the button is clicked.
 */
@Composable
fun IconButton(
    text: String,
    iconId: Int,
    interiorColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onClick: () -> Unit
) {
    val boxWidth = backgroundConfig.screenWidth * BUTTON_WIDTH_FACTOR
    val boxHeight = backgroundConfig.screenHeight * BUTTON_HEIGHT_FACTOR
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(roundedCornerShapeSize),
        colors = ButtonDefaults.buttonColors(containerColor = interiorColor),
        border = BorderStroke(
            width = borderWidth,
            color = borderColor
        ),
        modifier = Modifier
            .height(boxHeight)
            .width(boxWidth)
            .testTag(text)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(iconTextSpacerWidth))
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.inversePrimary,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun IconButtonPreview() {
    IconButton("Find Match", R.drawable.play_button, interiorColor = EggShell, onClick = {})
}