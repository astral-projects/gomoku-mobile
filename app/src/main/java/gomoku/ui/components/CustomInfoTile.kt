package gomoku.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pdm.gomoku.R

private val cornerShapeSize = 30.dp
private val paddingSize = 3.dp
private val labelPadding = 20.dp
private val labelIconAndLabelTextPadding = 5.dp
private val imageSize = 40.dp
private val medalSize = 50.dp
private val rowSize = 50.dp

@Composable
fun CustomInfoTile(
    leadingIconId: Int? = null,
    leadingNumber: Int,
    labelIconId: Int,
    label: String,
    labelTextColor: Color = Color.Black,
    trailingNumber: Int,
    trailingIconId: Int,
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .height(rowSize)
            .background(MaterialTheme.colorScheme.primary)
            .padding(paddingSize)
            .fillMaxWidth()
    ) {
        // leading icon and leading number
        Row {
            if (leadingIconId != null) {
                Image(
                    painter = painterResource(leadingIconId),
                    contentDescription = null,
                    modifier = Modifier.size(medalSize)
                )
            } else {
                Spacer(modifier = Modifier.padding(9.dp))
                // Hidden image to keep the layout
                Text(
                    text = leadingNumber.toString(),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }

        Row(
            modifier = Modifier
                .padding(start = labelPadding, end = labelPadding)
        ) {
            // label icon and label
            Image(
                painter = painterResource(labelIconId),
                contentDescription = null,
                modifier = Modifier.size(imageSize)
            )
            Spacer(modifier = Modifier.padding(labelIconAndLabelTextPadding))
            Text(
                text = label,
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                color = labelTextColor,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        Row {
            // trailing number and trailing icon
            Text(trailingNumber.toString(), style = MaterialTheme.typography.titleMedium)
            Image(
                painter = painterResource(trailingIconId),
                contentDescription = null,
                alpha = 0.9f,
                modifier = Modifier.size(imageSize)
            )
        }
    }
}

@Composable
@Preview
fun CustomInfoPreviewWithLeadingIcon() {
    CustomInfoTile(
        leadingIconId = R.drawable.gold_medal,
        leadingNumber = 1,
        labelIconId = R.drawable.man,
        label = "Geralt of Rivia",
        trailingNumber = 5432,
        trailingIconId = R.drawable.coins
    )
}

@Composable
@Preview
fun CustomInfoPreviewWithoutLeadingIcon() {
    CustomInfoTile(
        leadingNumber = 4,
        labelIconId = R.drawable.man,
        label = "Geralt of Rivia",
        trailingNumber = 100,
        trailingIconId = R.drawable.coins
    )
}