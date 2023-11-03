package gomoku.ui.components.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.components.generic.CustomIconChip

@Composable
fun GameInfoChip(
    leadingIconId: Int,
    label: String
) {
    CustomIconChip(
        leadingIconId = leadingIconId,
        label = label,
        textColor = Color.White,
        useSecondaryColor = true
    )
}

@Composable
@Preview
private fun GameInfoChipPreview() {
    GameInfoChip(
        leadingIconId = 0,
        label = "03:01"
    )
}