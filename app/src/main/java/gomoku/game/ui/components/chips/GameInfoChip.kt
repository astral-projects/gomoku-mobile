package gomoku.game.ui.components.chips

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.shared.components.CustomIconChip

/**
 * A [CustomIconChip] that displays game information.
 * @param leadingIconId The id of the leading icon.
 * @param label The text to display.
 */
@Composable
fun GameInfoChip(
    leadingIconId: Int,
    label: String
) = CustomIconChip(
    leadingIconId = leadingIconId,
    label = label,
    textColor = MaterialTheme.colorScheme.onPrimary,
    useSecondaryColor = true
)

@Composable
@Preview
private fun GameInfoChipPreview() {
    GameInfoChip(
        leadingIconId = 0,
        label = "03:01"
    )
}