package gomoku.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gomoku.shared.background.BackgroundConfig
import gomoku.shared.theme.ChipColors
import pdm.gomoku.R

// Config
private val chipCornerShapeSize = 10.dp
private val defaultChipBorderWidth = 2.dp
private const val CHIP_HEIGHT_FACTOR = 0.05f

/**
 * An [AssistChip] with customizable assets.
 * @param modifier Modifier to be applied to the chip.
 * @param textModifier Modifier to be applied to the text.
 * @param backgroundConfig The background configuration of the screen.
 * @param leadingIconId The id of the leading icon.
 * @param label The text to display.
 * @param textColor The color of the text.
 * @param borderWidth The width of the chip's border.
 * @param useSecondaryColor Indicates whether to use MaterialTheme's secondary color, or
 * to let the selected state determine the color.
 * Using the secondary color disables the selected color meaning of the chip.
 * @param select Indicates whether the chip is selected.
 * @param trailingIconId The id of the trailing icon.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomIconChip(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    backgroundConfig: BackgroundConfig = BackgroundConfig(
        LocalConfiguration.current
    ),
    leadingIconId: Int,
    label: String,
    textColor: Color = MaterialTheme.colorScheme.inversePrimary,
    borderWidth: Dp = defaultChipBorderWidth,
    useSecondaryColor: Boolean = false,
    select: Boolean = false,
    trailingIconId: Int? = null
) {
    val chipHeight = backgroundConfig.screenHeight * CHIP_HEIGHT_FACTOR
    AssistChip(
        modifier = modifier.height(chipHeight),
        onClick = { },
        shape = RoundedCornerShape(chipCornerShapeSize),
        label = {
            Text(
                modifier = textModifier,
                text = label,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                color = textColor
            )
        },
        leadingIcon = {
            Image(
                painter = painterResource(leadingIconId),
                contentDescription = null
            )
        },
        trailingIcon = {
            trailingIconId?.let {
                Image(
                    painter = painterResource(trailingIconId),
                    contentDescription = null
                )
            }
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (useSecondaryColor) MaterialTheme.colorScheme.secondary else
                if (select) ChipColors.SelectedInterior else ChipColors.UnselectedInterior,
        ),
        border = if (select)
            AssistChipDefaults.assistChipBorder(
                borderColor = ChipColors.SelectedBorder,
                borderWidth = borderWidth
            ) else AssistChipDefaults.assistChipBorder(
            borderColor = ChipColors.UnselectedBorder,
            borderWidth = borderWidth
        )
    )
}

@Composable
@Preview
private fun SelectedCustomIconChipPreview() {
    CustomIconChip(
        label = "01:09",
        leadingIconId = R.drawable.timer,
        select = true,
        trailingIconId = R.drawable.white_circle
    )
}

@Composable
@Preview
private fun UnselectedCustomIconChipPreview() {
    CustomIconChip(
        label = "01:09",
        leadingIconId = R.drawable.timer,
        textColor = Color.White,
        select = false
    )
}

@Composable
@Preview
private fun SecondaryColorCustomIconChipPreview() {
    CustomIconChip(
        label = "01:09",
        leadingIconId = R.drawable.timer,
        useSecondaryColor = true
    )
}