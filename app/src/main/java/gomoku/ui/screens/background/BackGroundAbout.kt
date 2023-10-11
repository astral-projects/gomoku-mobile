package gomoku.ui.screens.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gomoku.ui.lib.TextWithFont
import gomoku.ui.theme.GomokuTheme
import gomoku.ui.theme.PaynesGrey

@Composable
fun BackGroundAbout() {
    GomokuTheme {
        Surface(
            color = PaynesGrey,
        ) {
            val background = BackgroundConfig(LocalConfiguration.current)
            val yellowSquareHeight = background.screenHeight / 9
            val aboutTextSize: TextUnit = (
                    (yellowSquareHeight / 4.0f).value + (yellowSquareHeight / 8.0f).value
                    ).sp
            val cornerShapeSize = 30.dp

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = cornerShapeSize,
                                bottomStart = cornerShapeSize
                            )
                        )
                        .background(PaynesGrey)
                        .fillMaxWidth()
                        .size(yellowSquareHeight),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // TODO(Add the symbol here for pop up menu)

                    TextWithFont(text = "About", textSize = aboutTextSize)
                    // TODO(Add the authors name in the bottom of the screen)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun BackGroundAboutPreview() {
    BackGroundAbout()
}