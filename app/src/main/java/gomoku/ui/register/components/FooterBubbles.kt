package gomoku.ui.register.components

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
import gomoku.ui.shared.components.Bubble
import gomoku.ui.shared.theme.BubbleColors
import gomoku.ui.shared.theme.DarkOrange
import gomoku.ui.shared.theme.LightOrange

// Constants
const val BIG_DARKER_BUBBLE_RADIUS = 80f
const val SMALL_DARKER_BUBBLE_RADIUS = 15f
const val BIG_LIGHTER_BUBBLE_RADIUS = 80f
const val SMALL_LIGHTER_BUBBLE_RADIUS = 40f

// Config
private val footerBubblesLeftPanelBottomPadding = 20.dp
private val footerBubblesLeftPanelStartPadding = 20.dp
private val footerBubblesRightPanelBottomPadding = 75.dp
private val footerBubblesRightPanelEndPadding = 30.dp

/**
 * A set of [Bubble]s to be used in a screen's footer.
 */
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
                .padding(
                    start = footerBubblesLeftPanelStartPadding,
                    bottom = footerBubblesLeftPanelBottomPadding
                ),
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
                .padding(
                    bottom = footerBubblesRightPanelBottomPadding,
                    end = footerBubblesRightPanelEndPadding
                )
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