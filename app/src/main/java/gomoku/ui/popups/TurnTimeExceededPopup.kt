package gomoku.ui.popups

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.SubmitButton
import gomoku.ui.theme.LightOrange
import pdm.gomoku.R

// Config
private val popupPadding = 8.dp
private val titleSpacerWidth = 8.dp
private val bodyMsgPadding = 8.dp

/**
 * Pop up that will be shown when the user's turn time is exceeded.
 * @param background configuration of the background.
 * @param onDismissRequest callback to be invoked when the user wants to dismiss the popup.
 */
@Composable
fun TurnTimeExceedPopup(
    background: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onDismissRequest: () -> Unit = {}
) {
    DomainPopup(onDismissRequest = onDismissRequest) {
        val screenWidth = background.screenWidth
        Box(
            modifier = Modifier
                .width(screenWidth * POPUP_WIDTH_FACTOR)
                .clip(RoundedCornerShape(popupRoundCornerShapeSize))
                .background(MaterialTheme.colorScheme.primary)
                .padding(popupPadding)
                .clickable(onClick = onDismissRequest)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Popup.TurnTimeExceed.TITLE,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(titleSpacerWidth))
                    Image(
                        painterResource(id = R.drawable.timer_up),
                        contentDescription = null
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bodyMsgPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Popup.TurnTimeExceed.BODY_MSG,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    SubmitButton(
                        backgroundColor = LightOrange,
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        onButtonText = Popup.TurnTimeExceed.BUTTON_MSG,
                        onClick = onDismissRequest
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TurnTimeExceedPopupPreview() {
    TurnTimeExceedPopup()
}

