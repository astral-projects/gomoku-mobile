package gomoku.ui.shared.dialogs

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import gomoku.ui.shared.background.BackgroundConfig
import pdm.gomoku.R

// Config
private val dialogRoundCornerShapeSize = 20.dp
private const val DIALOG_WIDTH_FACTOR = 0.95f
private const val TEXT_WEIGHT_FACTOR = 0.6f
private val textHorizontalPadding = 15.dp
private val dialogPadding = 8.dp
private val iconSize = 50.dp

/**
 * Dialog that will be shown when the user is in the lobby waiting for an opponent.
 * @param background configuration of the background.
 * @param playerIconId id of the player's icon.
 * @param onDismissRequest callback to be invoked when the user wants to dismiss the popup.
 */
@Composable
fun WaitingOpponentDialog(
    background: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    playerIconId: Int,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        val screenWidth = background.screenWidth
        val cornerShape = RoundedCornerShape(dialogRoundCornerShapeSize)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .width(screenWidth * DIALOG_WIDTH_FACTOR)
                .clip(cornerShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(dialogPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onDismissRequest),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painterResource(id = playerIconId),
                    contentDescription = null,
                    modifier = Modifier.requiredSize(iconSize)
                )
                Text(
                    modifier = Modifier
                        .weight(TEXT_WEIGHT_FACTOR)
                        .padding(horizontal = textHorizontalPadding),
                    text = stringResource(id = gomoku.domain.dialog.Dialog.WaitingOpponent.TITLE),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
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
    WaitingOpponentDialog(
        playerIconId = R.drawable.man5,
        onDismissRequest = {}
    )
}
