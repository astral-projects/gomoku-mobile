package gomoku.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import pdm.gomoku.R

@Composable
fun ClickableIcon(
    iconId: Int,
    onClick: () -> Unit = {}
) {
    Image(
        painter = painterResource(id = iconId),
        contentScale = ContentScale.FillHeight,
        contentDescription = null,
        modifier = Modifier
            .clickable { onClick() }
    )
}

@Composable
@Preview
fun ClickableIconPreview() {
    ClickableIcon(
        iconId = R.drawable.target,
        onClick = {}
    )
}