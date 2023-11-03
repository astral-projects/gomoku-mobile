package gomoku.ui.popups

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
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.SubmitButton

// Constants
private const val MESSAGE =
    "Are you sure you want to quit this game? You won't receive any points as a result."
private const val LEAVE_MSG = "Yes"
private const val CONTINUE_MSG = "Nevermind"

// Config
private val PopupPadding = 8.dp
private val TextSpacerHeight = 8.dp
private val TextPadding = 8.dp

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
                .clip(RoundedCornerShape(PopupRoundCornerShapeSize))
                .background(MaterialTheme.colorScheme.primary)
                .width(screenWidth * PopupWidthFactor)
                .padding(PopupPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(TextPadding)
                ) {
                    Text(
                        text = MESSAGE,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Spacer(modifier = Modifier.height(TextSpacerHeight))
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SubmitButton(
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                        textColor = textColor,
                        onButtonText = CONTINUE_MSG,
                        onClick = onContinueRequest
                    )
                    SubmitButton(
                        backgroundColor = MaterialTheme.colorScheme.error,
                        textColor = textColor,
                        onButtonText = LEAVE_MSG,
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