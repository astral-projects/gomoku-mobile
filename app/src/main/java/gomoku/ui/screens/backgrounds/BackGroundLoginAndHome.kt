package gomoku.ui.screens.backgrounds

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gomoku.ui.GAME_NAME
import gomoku.ui.lib.TextWithFont
import gomoku.ui.theme.BlueBackGround
import gomoku.ui.theme.GomokuTheme
import gomoku.ui.theme.YellowBackGround
import pdm.gomoku.R

@Composable
fun BackGroundLoginAndHome() {
    GomokuTheme {
        Surface(
            color = BlueBackGround,
        ) {
           val background = BackGround(LocalConfiguration.current)
            val yellowSquareHeight = background.screenHeight / 3 + background.screenHeight / 20
            val yellowSquareCornerSize = 80.dp
            val gomokuLogo = background.gomokuLogo
            val gomokuLogoWidth = (background.screenWidth.value / 1.75).dp
            val gomokuLogoHeight = (yellowSquareHeight.value / 1.75).dp

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = yellowSquareCornerSize,
                                bottomStart = yellowSquareCornerSize
                            )
                        )
                        .background(YellowBackGround)
                        .size(background.screenWidth, yellowSquareHeight),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = 25.dp)
                    ) {
                        Image(
                            painterResource(gomokuLogo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(gomokuLogoWidth, gomokuLogoHeight)
                        )
                    }
                    TextWithFont(
                        text = GAME_NAME,
                        30.sp,
                        modifier = Modifier
                            .padding(top = 20.dp)
                    )
                    // TODO(Add the authors name in the bottom of the screen)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BackGroundLoginAndHomePreview() {
    BackGroundLoginAndHome()
}