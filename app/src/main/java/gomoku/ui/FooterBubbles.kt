package gomoku.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.components.domain.Bubble
import gomoku.ui.theme.BubbleColors

const val BIG_ORANGE_BUBBLE_RADIUS = 100f
const val SMALL_ORANGE_BUBBLE_RADIUS = 20f
const val BIG_BLUE_BUBBLE_RADIUS = 100f
const val SMALL_BLUE_BUBBLE_RADIUS = 50f
private val rightBubblesBottomPadding = 55.dp

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

            horizontalAlignment = Alignment.End,
        ) {
            // Small lighter bubble
            Bubble(
                radius = SMALL_BLUE_BUBBLE_RADIUS,
                interiorColor = BubbleColors.LighterBubbleInterior,
                borderColor = BubbleColors.LighterBubbleBorder
            )
            // Big darker bubble
            Bubble(
                radius = BIG_ORANGE_BUBBLE_RADIUS,
                interiorColor = BubbleColors.DarkerBubbleInterior,
                borderColor = BubbleColors.DarkerBubbleBorder
            )
            // Small darker bubble
            Bubble(
                radius = SMALL_ORANGE_BUBBLE_RADIUS,
                interiorColor = BubbleColors.DarkerBubbleInterior,
                borderColor = BubbleColors.DarkerBubbleBorder
            )

        }
        Column(
            modifier = Modifier
                .padding(bottom = rightBubblesBottomPadding)
        ) {
            // Small darker ball
            Bubble(
                radius = SMALL_ORANGE_BUBBLE_RADIUS,
                interiorColor = BubbleColors.DarkerBubbleInterior,
                borderColor = BubbleColors.DarkerBubbleBorder
            )
            // Big lighter bubble
            Bubble(
                radius = BIG_BLUE_BUBBLE_RADIUS,
                interiorColor = BubbleColors.LighterBubbleInterior,
                borderColor = BubbleColors.LighterBubbleBorder
            )
        }
    }
}