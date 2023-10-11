package gomoku.ui.lib

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.screens.backgrounds.BackGround
import gomoku.ui.theme.YellowBackGround
import gomoku.ui.theme.loginComposableBorder
import pdm.gomoku.R

/**
 * Composable of the Inputs Text for Login and Register
 * @param text - The Text of the Input
 * @param logo - The Image Logo to use in the image Composable
 * @param image - The Image Composable. Receives the logo as parameter
 *
 */
@Composable
fun InputTextEditor(
    text: String,
    logo: Int,
    modifier: Modifier = Modifier,
    image: @Composable (logo: Int) -> Unit
) {
    val background = BackGround(LocalConfiguration.current)
    val boxWidth = background.screenWidth * 0.6f
    val boxHeight = background.screenHeight * 0.05f
    val itemsWidth = boxWidth * 0.1f
    val itemsHeight = boxHeight * 0.5f
    val textWidth = boxWidth * 0.8f
    val textHeight = boxHeight * 0.8f
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(boxHeight)
            .background(YellowBackGround)
            .border(2.dp, loginComposableBorder, RectangleShape)
            .width(boxWidth)
    ) {
        // Image
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(start = 10.dp)
                .size(itemsWidth, itemsHeight)
        ){
            image(logo)
        }
        // Text
        Column (
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .padding(start = 20.dp)
                .size(textWidth, textHeight)
        ){
            TextWithFont(text)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun InputTextEditorPreview() {
    InputTextEditor("User", R.drawable.user) { logo ->
        Image(
            painterResource(logo),
            contentDescription = "User Logo",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}