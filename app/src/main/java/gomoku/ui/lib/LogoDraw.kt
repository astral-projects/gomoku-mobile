package gomoku.ui.lib

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun LogoDraw(
    id: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id),
        contentDescription = description,
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
    )
}