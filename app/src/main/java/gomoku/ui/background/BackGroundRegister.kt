package gomoku.ui.background

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gomoku.ui.GAME_NAME
import gomoku.ui.components.domain.Bubble
import gomoku.ui.components.generic.TextWithFont
import gomoku.ui.theme.GomokuTheme
import gomoku.ui.theme.PaynesGrey
import gomoku.ui.theme.DarkBlue
import gomoku.ui.theme.LightBlue
import gomoku.ui.theme.DarkOrange
import gomoku.ui.theme.LightOrange

@Composable
fun BackGroundRegister() {
    GomokuTheme {
        Surface(
            color = PaynesGrey,
        ) {
            val background = BackgroundConfig(LocalConfiguration.current)
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
                        .background(PaynesGrey)
                        .fillMaxWidth()
                        .size(yellowSquareHeight),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // TODO(Add the symbol here for pop up menu)

                    TextWithFont(
                        text = GAME_NAME,
                        textSize = gameNameTextSize,
                        modifier = Modifier
                            .padding(top = 20.dp)
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
                        interiorColor = LightBlue,
                        borderColor = DarkBlue
                    )
                    // Big orange bubble
                    Bubble(
                        radius = bigOrangeBubbleRadius,
                        interiorColor = LightOrange,
                        borderColor = DarkOrange
                    )
                    // Small orange bubble
                    Bubble(
                        radius = smallOrangeBubbleRadius,
                        interiorColor = LightOrange,
                        borderColor = DarkOrange
                    )

                }
                Column (
                    modifier = Modifier
                        .padding(bottom = 75.dp, end = 30.dp)
                ){
                    // Small orange ball
                    Bubble(
                        radius = smallOrangeBubbleRadius,
                        interiorColor = LightOrange,
                        borderColor = DarkOrange
                    )
                    // Big blue bubble
                    Bubble(
                        radius = bigBlueBubbleRadius,
                        interiorColor = LightBlue,
                        borderColor = DarkBlue
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BackGroundRegisterPreview() {
    BackGroundRegister()
}
