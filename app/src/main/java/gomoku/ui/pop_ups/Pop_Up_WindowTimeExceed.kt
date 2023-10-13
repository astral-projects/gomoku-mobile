package gomoku.ui.pop_ups

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import gomoku.ui.components.generic.DismissButton
import gomoku.ui.components.generic.TextWithFont
import gomoku.ui.theme.LightOrange
import pdm.gomoku.R


@Composable
fun Pop_Up_WindowTimeExceed(background: BackgroundConfig) {
    Box(
        modifier = Modifier
            .width(background.screenWidth * 0.8f)
            .height(background.screenHeight * 0.25f)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(15.dp)
            .clickable(onClick = {
            }),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithFont(text = "Turn Time Exceeded")
                Image(
                    painterResource(id = R.drawable.timer_up),
                    modifier = Modifier.padding(start = 8.dp),
                    contentDescription = null
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithFont(
                    text = "Your strategic moves were on point" +
                            ",but this time, the clock got better of you. "
                            + "Unfortunately, no points can be awarded as a result.",
                )
            }
            Row {
                DismissButton(
                    backgroundColor = LightOrange,
                    letterColor = Color.White,
                    onButtonText = "Acknowledge",
                    enable = true,
                    onDismiss = {}
                )
            }

        }
    }
}


@Composable
@Preview(showBackground = true)
fun Pop_Up_WindowTimeExceed_Preview() {
    Pop_Up_WindowTimeExceed(background = BackgroundConfig(LocalConfiguration.current))
}

