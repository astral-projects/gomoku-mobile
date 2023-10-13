package gomoku.ui.components.generic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.theme.EggShell
import gomoku.ui.theme.loginComposableBorder
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
    text: String,
    iconId: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = EggShell,
    backgroundConfig: BackgroundConfig
) {
    val boxWidth = backgroundConfig.screenWidth * 0.7f
    val boxHeight = backgroundConfig.screenHeight * 0.05f
    val iconWidth = boxWidth * 0.1f
    val iconHeight = boxHeight * 0.5f
    val textWidth = boxWidth * 0.8f
    val textHeight = boxHeight * 0.8f
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(boxHeight)
            .background(backgroundColor)
            .border(2.dp, loginComposableBorder, RectangleShape)
            .width(boxWidth)
    ) {
        // Image
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(start = 10.dp)
                .size(iconWidth, iconHeight)
        ) {
            Image(
                painter = painterResource(iconId),
                contentDescription = null
            )
        }
        // Text
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .padding(start = 20.dp)
                .size(textWidth, textHeight)
        ) {
            TextWithFont(text)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun InputTextEditorPreview() {
    InputTextEditor(
        text = "User",
        iconId = R.drawable.user,
        backgroundColor = Color.Cyan,
        backgroundConfig = BackgroundConfig(LocalConfiguration.current)
    )
}