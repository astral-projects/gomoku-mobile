package gomoku.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pdm.gomoku.R

private val cornerShapeSize = 10.dp
private val paddingSize = 3.dp
private val labelPadding = 20.dp
private val labelIconAndLabelTextPadding = 5.dp
private val imageSize = 40.dp
private val rowSize = 60.dp
private val paddingNumber= 9.dp
private val paddingNumber2= 8.dp

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
            .clip(RoundedCornerShape(cornerShapeSize))
            .height(rowSize)
            .background(MaterialTheme.colorScheme.primary)
            .padding(paddingSize)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(rowSize)
        ) {
            if (leadingIconId != null) {
                Image(
                    painter = painterResource(leadingIconId),
                    contentDescription = null,
                    modifier = Modifier.size(imageSize)
                )
            } else {
                Spacer(modifier = Modifier.padding(paddingNumber))
                Text(
                    text = leadingNumber.toString(),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.padding(paddingNumber2))
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(rowSize)
                .padding(start = labelPadding, end = labelPadding)
        ) {
            Image(
                painter = painterResource(labelIconId),
                contentDescription = null,
                modifier = Modifier.size(imageSize)
            )
            Spacer(modifier = Modifier.padding(labelIconAndLabelTextPadding))
            //TODO(It is difficult to make the text wrap, so we are limiting the size of the text)
            Text(
                text = if (label.length > 8) {
                    label.substring(0, 8) + "..."
                } else {
                    label
                },
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                color = labelTextColor,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(rowSize)
            ) {
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
        label = "Player 1asdasdadsasd",
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