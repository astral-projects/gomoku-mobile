package gomoku.shared.popups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.shared.background.BackgroundConfig
import gomoku.shared.components.SubmitButton

// Config
private val popupPadding = 8.dp
private val textSpacerHeight = 8.dp
private val textPadding = 8.dp

/**
 * Pop up that will be shown when the user wants to leave the game.
 * @param backgroundConfig configuration of the background.
 * @param onContinueRequest callback to be invoked when the user wants to continue the game.
 * @param onLeaveRequest callback to be invoked when the user wants to leave the game.
 */
@Composable
fun LeaveGamePopup(
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onContinueRequest: () -> Unit = {},
    onLeaveRequest: () -> Unit = {}
) {
    val textColor = MaterialTheme.colorScheme.onPrimary
    val screenWidth = backgroundConfig.screenWidth
    DomainPopup(onDismissRequest = onContinueRequest) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(popupRoundCornerShapeSize))
                .background(MaterialTheme.colorScheme.primary)
                .width(screenWidth * POPUP_WIDTH_FACTOR)
                .padding(popupPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(textPadding)
                ) {
                    Text(
                        text = Popup.LeaveGame.MESSAGE,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Spacer(modifier = Modifier.height(textSpacerHeight))
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SubmitButton(
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                        textColor = textColor,
                        onButtonText = Popup.LeaveGame.CONTINUE_MSG,
                        onClick = onContinueRequest
                    )
                    SubmitButton(
                        backgroundColor = MaterialTheme.colorScheme.error,
                        textColor = textColor,
                        onButtonText = Popup.LeaveGame.LEAVE_MSG,
                        onClick = onLeaveRequest
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun LeaveGamePopupPreview() {
    LeaveGamePopup()
}