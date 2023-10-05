package gomoku.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import gomoku.ui.lib.Bubble
import gomoku.ui.theme.BlueBackGround
import gomoku.ui.theme.GomokuTheme
import gomoku.ui.theme.YellowBackGround
import gomoku.ui.theme.blueBubbleBorder
import gomoku.ui.theme.blueBubbleInterior
import gomoku.ui.theme.orangeBubbleBorder
import gomoku.ui.theme.orangeBubbleInterior
import pdm.gomoku.R

@Composable
fun BackGroundRegister() {
    GomokuTheme {
        Surface(
            color = BlueBackGround,
        ) {
            val background = BackGround(LocalConfiguration.current)
            val yellowSquareHeight = background.screenHeight / 6
            val gameNameTextSize: TextUnit = (yellowSquareHeight.value / 5.0f).sp
            val cornerShapeSize = 30.dp
            val bigOrangeBubbleRadius = 100f
            val smallOrangeBubbleRadius = 20f
            val bigBlueBubbleRadius = 100f
            val smallBlueBubbleRadius = 50f

            Box(
                modifier = Modifier
                    .fillMaxSize(),
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
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // TODO(Add the symbol here for pop up menu)

                    Text(
                        text = GAME_NAME,
                        fontSize = gameNameTextSize,
                        fontFamily = FontFamily(
                            Font(
                                R.font.varelaround_regular,
                                FontWeight.ExtraBold
                            )
                        ),
                        modifier = Modifier
                            .padding(top = 22.dp)
                    )
                }
            }
            // TODO(Rearrange the bubbles padding with window size and not dp's)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column (
                    modifier = Modifier
                        .padding(bottom = 20.dp, start = 20.dp),
                    horizontalAlignment = Alignment.End,
                ){
                    // Small blue bubble
                    Bubble(
                        radius = smallBlueBubbleRadius,
                        interiorColor = blueBubbleInterior,
                        borderColor = blueBubbleBorder
                    )
                    // Big orange bubble
                    Bubble(
                        radius = bigOrangeBubbleRadius,
                        interiorColor = orangeBubbleInterior,
                        borderColor = orangeBubbleBorder
                    )
                    // Small orange bubble
                    Bubble(
                        radius = smallOrangeBubbleRadius,
                        interiorColor = orangeBubbleInterior,
                        borderColor = orangeBubbleBorder
                    )

                }
                Column (
                    modifier = Modifier
                        .padding(bottom = 75.dp, end = 30.dp)
                ){
                    // Small orange ball
                    Bubble(
                        radius = smallOrangeBubbleRadius,
                        interiorColor = orangeBubbleInterior,
                        borderColor = orangeBubbleBorder
                    )
                    // Big blue bubble
                    Bubble(
                        radius = bigBlueBubbleRadius,
                        interiorColor = blueBubbleInterior,
                        borderColor = blueBubbleBorder
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun BackGroundRegisterPreview() {
    BackGroundRegister()
}
