package gomoku.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gomoku.ui.GAME_NAME
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
                        Text(
                            text = GAME_NAME,
                            fontSize = 30.sp,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.varelaround_regular,
                                    FontWeight.Bold
                                )
                            ),
                            modifier = Modifier
                                .padding(top = 20.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 10.dp)
                        ,
                        contentAlignment = Alignment.TopCenter // Centralizando o conteúdo no Box
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Welcome Back User...",
                                color = Color.Black,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.varelaround_regular,
                                        FontWeight.Bold
                                    )
                                ),)
                            Spacer(modifier = Modifier.padding(10.dp))
                            cliqueableRow(text = "Find Match",R.drawable.play_button ,background)
                            Spacer(modifier = Modifier.padding(10.dp))
                            cliqueableRow(text = "LeaderBoards",R.drawable.leaderboard, background)
                            Spacer(modifier = Modifier.padding(10.dp))
                            cliqueableRow(text = "About",R.drawable.about, background)
                            Spacer(modifier = Modifier.padding(10.dp))
                            cliqueableRow(text = "Logout",R.drawable.door_out, background)
                        }
                    }
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


@Composable
fun cliqueableRow(text: String,image:Int ,background: BackGround) {
    Row(
        modifier = Modifier
            .width(background.screenWidth * 0.8f)
            .height(background.screenHeight * 0.07f)
            .clip(RoundedCornerShape(12.dp))
            .background(YellowBackGround)
            .padding(15.dp)
            .clickable(onClick = {
            }),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(image),
            contentDescription = null,
            )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = Color.DarkGray,
            fontFamily = FontFamily(
                Font(
                    R.font.varelaround_regular,
                    FontWeight.Bold
                )
            ),
        )
    }
}