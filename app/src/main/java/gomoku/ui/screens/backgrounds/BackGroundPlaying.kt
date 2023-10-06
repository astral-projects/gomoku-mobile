package gomoku.ui.screens.backgrounds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gomoku.ui.GAME_NAME
import gomoku.ui.lib.TextWithFont
import gomoku.ui.theme.BlueBackGround
import gomoku.ui.theme.GomokuTheme
import gomoku.ui.theme.YellowBackGround
import pdm.gomoku.R

@Composable
fun BackGroundPlaying() {
    GomokuTheme {
        Surface(
            color = BlueBackGround,
        ) {
            val background = BackGround(LocalConfiguration.current)
            val yellowSquareHeight = background.screenHeight / 9
            val gameNameTextSize: TextUnit = (yellowSquareHeight.value / 4.0f).sp
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
                        .background(YellowBackGround)
                        .fillMaxWidth()
                        .size(yellowSquareHeight),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // TODO(Add the symbol here for pop up menu)

                    TextWithFont(text = GAME_NAME, textSize = gameNameTextSize)

                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BackGroundPlayingPreview() {
    BackGroundPlaying()
}