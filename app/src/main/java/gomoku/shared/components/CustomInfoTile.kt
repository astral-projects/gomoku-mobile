package gomoku.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pdm.gomoku.R

// Config
private val cornerShapeSize = 15.dp
private val tilePadding = 10.dp
private val labelIconSpacerWidth = 5.dp
private const val LEADING_SECTION_WEIGHT = 0.14f
private const val LABEL_SECTION_WEIGHT = 0.50f
private const val SPACER_WEIGHT_BETWEEN_LABEL_AND_TRAILING_SECTION = 0.01f
private const val TRAILING_SECTION_WEIGHT = 0.35f

/**
 * A tile with custom information.
 * @param leadingIconId The id of the leading icon.
 * @param leadingLabel The label to be displayed in the leading position.
 * @param labelIconId The id of the label icon.
 * @param label The label to be displayed.
 * @param trailingLabel The label to be displayed in the trailing position.
 * @param trailingIconId The id of the trailing icon.
 */
@Composable
fun <T> CustomInfoTile(
    data: T,
    leadingIconId: Int? = null,
    leadingLabel: String,
    labelIconId: Int,
    label: String,
    trailingLabel: String,
    trailingIconId: Int,
    // onClick receives a parameter of some type T and returns a Unit.
    onClick: (T) -> Unit
) {
    val style = MaterialTheme.typography.titleMedium
    val color = MaterialTheme.colorScheme.inversePrimary
    val fontWeight = FontWeight.SemiBold
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerShapeSize))
            .background(MaterialTheme.colorScheme.primary)
            .padding(tilePadding)
            .clickable { onClick(data) }
    ) {
        // Leading section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(LEADING_SECTION_WEIGHT)
        ) {
            if (leadingIconId != null) {
                Image(
                    painter = painterResource(leadingIconId),
                    contentDescription = null
                )
            } else {
                Text(
                    text = leadingLabel,
                    style = style,
                    color = color,
                    fontWeight = fontWeight,
                    textAlign = TextAlign.Center,
                )
            }
        }
        // Label section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(labelIconSpacerWidth),
            modifier = Modifier.weight(LABEL_SECTION_WEIGHT)
        ) {
            Image(
                painter = painterResource(labelIconId),
                contentDescription = null
            )
            Text(
                text = label,
                maxLines = 1,
                style = style,
                color = color,
                fontWeight = fontWeight,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.weight(SPACER_WEIGHT_BETWEEN_LABEL_AND_TRAILING_SECTION))
        // Trailing section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(TRAILING_SECTION_WEIGHT)
        ) {
            Text(
                text = trailingLabel,
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            Image(
                painter = painterResource(trailingIconId),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
private fun CustomInfoPreviewWithLeadingIcon() {
    CustomInfoTile(
        data = "",
        leadingIconId = R.drawable.gold_medal,
        leadingLabel = "1",
        labelIconId = R.drawable.man,
        label = "Player".repeat(100),
        trailingLabel = "5432",
        trailingIconId = R.drawable.coins,
        onClick = {}
    )
}

@Composable
@Preview
private fun CustomInfoPreviewWithoutLeadingIcon() {
    CustomInfoTile(
        data = "",
        leadingLabel = "4",
        labelIconId = R.drawable.man,
        label = "Player".repeat(100),
        trailingLabel = "100",
        trailingIconId = R.drawable.coins,
        onClick = {}
    )
}