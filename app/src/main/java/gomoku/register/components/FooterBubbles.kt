package gomoku.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.components.Bubble
import gomoku.ui.theme.BubbleColors
import gomoku.ui.theme.DarkOrange
import gomoku.ui.theme.LightOrange

// Constants
const val BIG_DARKER_BUBBLE_RADIUS = 80f
const val SMALL_DARKER_BUBBLE_RADIUS = 15f
const val BIG_LIGHTER_BUBBLE_RADIUS = 80f
const val SMALL_LIGHTER_BUBBLE_RADIUS = 40f

@Composable
@Preview
fun FooterBubbles() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 20.dp, start = 20.dp),
            horizontalAlignment = Alignment.End,
        ) {
            // Small lighter bubble
            Bubble(
                radius = SMALL_LIGHTER_BUBBLE_RADIUS,
                interiorColor = BubbleColors.LighterBubbleInterior,
                borderColor = BubbleColors.LighterBubbleBorder
            )
            // Big darker bubble
            Bubble(
                radius = BIG_DARKER_BUBBLE_RADIUS,
                interiorColor = BubbleColors.DarkerBubbleInterior,
                borderColor = BubbleColors.DarkerBubbleBorder
            )
            // Small darker bubble
            Bubble(
                radius = SMALL_DARKER_BUBBLE_RADIUS,
                interiorColor = LightOrange,
                borderColor = DarkOrange
            )
        }
        Column(
            modifier = Modifier
                .padding(bottom = 75.dp, end = 30.dp)
        ) {
            // Small darker bubble
            Bubble(
                radius = SMALL_DARKER_BUBBLE_RADIUS,
                interiorColor = BubbleColors.DarkerBubbleInterior,
                borderColor = BubbleColors.DarkerBubbleBorder
            )
            // Big lighter bubble
            Bubble(
                radius = BIG_LIGHTER_BUBBLE_RADIUS,
                interiorColor = BubbleColors.LighterBubbleInterior,
                borderColor = BubbleColors.LighterBubbleBorder
            )
        }
    }
}