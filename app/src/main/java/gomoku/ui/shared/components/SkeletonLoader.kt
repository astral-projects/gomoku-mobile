package gomoku.ui.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import pdm.gomoku.R

/**
 * This is a generic skeleton loader that can be used to wrap any composable.
 * It will show a shimmer effect over the content while loading is true.
 * The content will be shown when loading is false.
 * @param loading: Boolean
 * @param contentView: @Composable () -> Unit
 * @return Unit
 */
@Composable
fun SkeletonLoader(
    loading: Boolean,
    contentView: @Composable () -> Unit,
) {
    if (loading) {
        SkeletonLoader(contentView = contentView)
    } else {
        contentView()
    }

}

/**
 * This is a skeleton loader that will show a shimmer effect over the content.
 * The shimmer effect will be the same size as the content.
 * @param contentView: @Composable () -> Unit
 * @return Unit
 */
@Composable
fun SkeletonLoader(contentView: @Composable () -> Unit) {
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    Box(
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                boxSize = coordinates.size
            }
    ) {
        contentView()
        Box(
            modifier = Modifier
                .matchParentSize()
                .shimmerEffect()
        )
    }
}


@Composable
@Preview
private fun SkeletonPreview() {
    SkeletonLoader(true) {
        CustomIconChip(
            label = "01:09",
            leadingIconId = R.drawable.timer,
            select = true,
            trailingIconId = R.drawable.white_circle,
        )
    }
}
