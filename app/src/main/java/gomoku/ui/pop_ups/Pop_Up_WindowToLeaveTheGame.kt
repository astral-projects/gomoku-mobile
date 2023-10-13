package gomoku.ui.screens.pop_ups

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.generic.TextWithFont

import pdm.gomoku.R

@Composable
@Preview(showBackground = true)
fun Pop_Up_WindowToLeaveTheGame_Preview() {
    Pop_Up_WindowToLeaveTheGame(BackgroundConfig(LocalConfiguration.current))
}

@Composable
fun Pop_Up_WindowToLeaveTheGame(background: BackgroundConfig) {
    Box(
        modifier = Modifier
            .width(background.screenWidth * 0.8f)
            .height(background.screenHeight * 0.2f)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(15.dp)
            .clickable(onClick = {
            }),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()

        ){
            Row {
                TextWithFont(
                    text = "Are you sure you want to quit this the game?" +
                            " You won't receive any points as a result", 3, 35
                )
            }
            Row(
            ) {
                DismissButton(backgroundColor = Color.Green, letterColor =Color.White , onButtonText = "Nevermind" , enable =true ) {
                }
                Spacer(modifier = Modifier.width(80.dp))
                DismissButton(backgroundColor = Color.Red, letterColor =Color.White , onButtonText = "Yes" , enable =true ) {
                }
            }

        }
    }
}
