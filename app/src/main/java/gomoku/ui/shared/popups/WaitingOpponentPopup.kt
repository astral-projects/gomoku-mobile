package gomoku.ui.shared.popups

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.shared.background.BackgroundConfig
import pdm.gomoku.R

// Config
private val popupPadding = 8.dp
private val iconSize = 75.dp

/**
 * Pop up that will be shown when the user is in lobby waiting for an opponent.
 * @param background configuration of the background.
 * @param playerIconId id of the player's icon.
 * @param onDismissRequest callback to be invoked when the user wants to dismiss the popup.
 */
@Composable
fun WaitingOpponentPopup(
    background: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    playerIconId: Int,
    onDismissRequest: () -> Unit = {}
) {
    DomainPopup(
        onDismissRequest = onDismissRequest,
    ) {
        val screenWidth = background.screenWidth
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .width(screenWidth * POPUP_WIDTH_FACTOR)
                .clip(RoundedCornerShape(popupRoundCornerShapeSize))
                .background(MaterialTheme.colorScheme.primary)
                .padding(popupPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onDismissRequest),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painterResource(id = playerIconId),
                    contentDescription = null,
                    modifier = Modifier
                        .requiredSize(iconSize)
                )
                Text(
                    modifier = Modifier.weight(0.6f),
                    text = Popup.WaitingOpponent.TITLE,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                Image(
                    painterResource(id = R.drawable.ellipsis),
                    contentDescription = null,
                    modifier = Modifier.requiredSize(iconSize)
                )
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
private fun WaitingOpponentPopupPreview() {
    WaitingOpponentPopup(
        playerIconId = R.drawable.man5
    )
}
