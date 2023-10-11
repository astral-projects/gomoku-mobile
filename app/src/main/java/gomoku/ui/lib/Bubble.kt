package gomoku.ui.lib

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A bubble for some background.
 * @param radius The radius of the bubble.
 * @param interiorColor The color of the interior of the bubble.
 * @param borderColor The color of the border of the bubble.
 */
@Composable
fun Bubble(radius: Float, interiorColor: Color, borderColor: Color) {
    val borderSize = 3.dp
    Box(
        // Small orange bubble
        modifier = Modifier
            .size(radius.dp)
            .clip(RoundedCornerShape(50))
            .background(interiorColor)
            .border(borderSize, borderColor, RoundedCornerShape(50))
    )
}

@Composable
@Preview(showBackground = true)
fun BubblePreview() {
    Bubble(50f, Color.Red, Color.Blue)
}