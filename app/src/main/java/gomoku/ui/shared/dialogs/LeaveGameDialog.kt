package gomoku.ui.shared.dialogs

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import gomoku.ui.shared.background.BackgroundConfig
import gomoku.ui.shared.components.SubmitButton


// Config
private val dialogRoundCornerShapeSize = 20.dp
private const val CONTINUE_MSG_WEIGHT_FACTOR = 0.7f
private const val LEAVE_MSG_WEIGHT_FACTOR = 1f - CONTINUE_MSG_WEIGHT_FACTOR
private const val DIALOG_WIDTH_FACTOR = 0.9f
private val horizontalPaddingBetweenButtons = 4.dp
private val dialogPadding = 8.dp
private val textSpacerHeight = 8.dp
private val textPadding = 8.dp

/**
 * Dialog that will be shown when the user wants to leave the game.
 * @param backgroundConfig configuration of the background.
 * @param onContinueRequest callback to be invoked when the user wants to continue the game.
 * @param onLeaveRequest callback to be invoked when the user wants to leave the game.
 */
@Composable
fun LeaveGameDialog(
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onContinueRequest: () -> Unit,
    onLeaveRequest: () -> Unit,
) {
    val textColor = MaterialTheme.colorScheme.onPrimary
    val screenWidth = backgroundConfig.screenWidth
    val cornerShape = RoundedCornerShape(dialogRoundCornerShapeSize)
    Dialog(onDismissRequest = onContinueRequest) {
        Box(
            modifier = Modifier
                .clip(cornerShape)
                .background(MaterialTheme.colorScheme.primary)
                .width(screenWidth * DIALOG_WIDTH_FACTOR)
                .padding(dialogPadding),
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
                        text = stringResource(id = gomoku.domain.dialog.Dialog.LeaveGame.MESSAGE),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Spacer(modifier = Modifier.height(textSpacerHeight))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SubmitButton(
                        modifier = Modifier.weight(CONTINUE_MSG_WEIGHT_FACTOR),
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                        textColor = textColor,
                        onButtonText = stringResource(id = gomoku.domain.dialog.Dialog.LeaveGame.CONTINUE_MSG),
                        onClick = onContinueRequest
                    )
                    Spacer(modifier = Modifier.padding(horizontal = horizontalPaddingBetweenButtons))
                    SubmitButton(
                        modifier = Modifier.weight(LEAVE_MSG_WEIGHT_FACTOR),
                        backgroundColor = MaterialTheme.colorScheme.error,
                        textColor = textColor,
                        onButtonText = stringResource(id = gomoku.domain.dialog.Dialog.LeaveGame.LEAVE_MSG),
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
    LeaveGameDialog(
        onContinueRequest = {},
        onLeaveRequest = {}
    )
}