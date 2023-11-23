package gomoku.game.ui.components.chips

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import gomoku.shared.components.CustomIconChip

/**
 * A [CustomIconChip] that displays game information.
 * @param modifier The modifier to be applied to the chip.
 * @param leadingIconId The id of the leading icon.
 * @param label The text to display.
 */
@Composable
fun GameInfoChip(
    modifier: Modifier = Modifier,
    leadingIconId: Int,
    label: String
) = CustomIconChip(
    modifier = modifier,
    leadingIconId = leadingIconId,
    label = label,
    textColor = MaterialTheme.colorScheme.onPrimary,
    useSecondaryColor = true
)

/*
fun gameionfoshipsELEIT (){
    SkeletonLoaderGeneric(loadinG = ) {
       GameInfoChip(leadingIconId = , label = )
    }
}
*/
@Composable
@Preview
private fun GameInfoChipPreview() {
    GameInfoChip(
        leadingIconId = 0,
        label = "03:01"
    )
}