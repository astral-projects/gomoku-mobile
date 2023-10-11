package gomoku.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.lib.Bubble
import gomoku.ui.theme.BlueBubbleBorder
import gomoku.ui.theme.BlueBubbleInterior
import gomoku.ui.theme.OrangeBubbleBorder
import gomoku.ui.theme.OrangeBubbleInterior

const val BIG_ORANGE_BUBBLE_RADIUS = 100f
const val SMALL_ORANGE_BUBBLE_RADIUS = 20f
const val BIG_BLUE_BUBBLE_RADIUS = 100f
const val SMALL_BLUE_BUBBLE_RADIUS = 50f

@Composable
@Preview
fun FooterBubbles() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 20.dp, start = 20.dp),
            horizontalAlignment = Alignment.End,
        ) {
            // Small blue bubble
            Bubble(
                radius = SMALL_BLUE_BUBBLE_RADIUS,
                interiorColor = BlueBubbleInterior,
                borderColor = BlueBubbleBorder
            )
            // Big orange bubble
            Bubble(
                radius = BIG_ORANGE_BUBBLE_RADIUS,
                interiorColor = OrangeBubbleInterior,
                borderColor = OrangeBubbleBorder
            )
            // Small orange bubble
            Bubble(
                radius = SMALL_ORANGE_BUBBLE_RADIUS,
                interiorColor = OrangeBubbleInterior,
                borderColor = OrangeBubbleBorder
            )

        }
        Column(
            modifier = Modifier
                .padding(bottom = 75.dp, end = 30.dp)
        ) {
            // Small orange ball
            Bubble(
                radius = SMALL_ORANGE_BUBBLE_RADIUS,
                interiorColor = OrangeBubbleInterior,
                borderColor = OrangeBubbleBorder
            )
            // Big blue bubble
            Bubble(
                radius = BIG_BLUE_BUBBLE_RADIUS,
                interiorColor = BlueBubbleInterior,
                borderColor = BlueBubbleBorder
            )
        }
    }
}