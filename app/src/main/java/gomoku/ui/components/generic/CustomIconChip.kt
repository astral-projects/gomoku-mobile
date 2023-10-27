package gomoku.ui.components.generic

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gomoku.ui.theme.GomokuTheme
import gomoku.ui.theme.ChipColors
import pdm.gomoku.R

private val ChipCornerShapeSize = 10.dp
private val DefaultChipBorderWidth = 2.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomIconChip(
    leadingIconId: Int,
    label: String,
    textColor: Color = Color.Black,
    borderWidth: Dp = DefaultChipBorderWidth,
    useSecondaryColor: Boolean = false,
    select: Boolean = false,
    trailingIconId: Int? = null
) {
    AssistChip(
        onClick = { },
        shape = RoundedCornerShape(ChipCornerShapeSize),
        label = {
            Text(
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
fun SelectedCustomIconChipPreview() {
    GomokuTheme {
        CustomIconChip(
            label = "01:09",
            leadingIconId = R.drawable.timer,
            select = true,
            trailingIconId = R.drawable.white_circle
        )
    }
}

@Composable
@Preview
fun UnselectedCustomIconChipPreview() {
    GomokuTheme {
        CustomIconChip(
            label = "01:09",
            leadingIconId = R.drawable.timer,
            textColor = Color.White,
            select = false
        )
    }
}

@Composable
@Preview
fun CustomChipWithPrimaryOnlyPreview() {
    GomokuTheme {
        CustomIconChip(
            label = "01:09",
            leadingIconId = R.drawable.timer,
            useSecondaryColor = true
        )
    }
}