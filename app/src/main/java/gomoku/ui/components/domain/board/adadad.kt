package gomoku.ui.components.domain.board

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// make a square without border with a cross inside like an intersection
@Composable
fun IntersectionComposable(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    size: Dp = 100.dp
) {
    Canvas(modifier = modifier) {
        val squareSize = size.toPx()
        val crossSize = squareSize / 3
        val crossOffset = (squareSize - crossSize) / 2
        drawRect(
            color = color,
            topLeft = Offset(0f, 0f),
            size = Size(squareSize, squareSize)
        )
        drawLine(
            color = Color.Red,
            start = Offset(crossOffset, 0f),
            end = Offset(crossOffset, squareSize),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.Blue,
            start = Offset(0f, crossOffset),
            end = Offset(squareSize, crossOffset),
            strokeWidth = 2f
        )
    }
}

@Composable
@Preview
fun IntersectionComposablePreview() {
    IntersectionComposable()
}