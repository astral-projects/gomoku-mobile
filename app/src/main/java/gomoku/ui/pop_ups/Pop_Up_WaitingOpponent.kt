package gomoku.ui.pop_ups

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
fun Pop_UP_WaitingOpponent  (background: BackgroundConfig) {
    Box(
        modifier = Modifier
            .width(background.screenWidth * 0.8f)
            .height(background.screenHeight * 0.07f)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
            .padding(15.dp)
            .clickable(onClick = {
            }),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painterResource(R.drawable.man),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)  // Takes up 1 part of available space
                    .size(400.dp)
            )
            TextWithFont(
                text = "Searching for an opponent",
                modifier = Modifier
                    .weight(2f)  // Takes up 2 parts of available space
                    .padding(horizontal = 8.dp)
            )
            Image(
                painterResource(id = R.drawable.ellipsis),
                modifier = Modifier
                    .weight(1f)  // Takes up 1 part of available space
                    .size(400.dp),
                contentDescription = null
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun Pop_UP_WaitingOpponent_Preview() {
    Pop_UP_WaitingOpponent(background =  BackgroundConfig(LocalConfiguration.current))
}
