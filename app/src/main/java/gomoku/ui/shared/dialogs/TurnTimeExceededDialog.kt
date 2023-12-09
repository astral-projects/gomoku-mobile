package gomoku.ui.shared.dialogs

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import gomoku.ui.shared.background.BackgroundConfig
import gomoku.ui.shared.components.SubmitButton
import gomoku.ui.shared.theme.LightOrange
import pdm.gomoku.R

// Config
private val dialogRoundCornerShapeSize = 20.dp
private const val DIALOG_WIDTH_FACTOR = 0.9f
private val dialogPadding = 8.dp
private val titleSpacerWidth = 8.dp
private val bodyMsgPadding = 8.dp

/**
 * Dialog that will be shown when the user's turn time is exceeded.
 * @param background configuration of the background.
 * @param onDismissRequest callback to be invoked when the user wants to dismiss the dialog.
 */
@Composable
fun TurnTimeExceedDialog(
    background: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onDismissRequest: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismissRequest) {
        val screenWidth = background.screenWidth
        val cornerShape = RoundedCornerShape(dialogRoundCornerShapeSize)
        Box(
            modifier = Modifier
                .width(screenWidth * DIALOG_WIDTH_FACTOR)
                .clip(cornerShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(dialogPadding)
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
                        text = stringResource(id = gomoku.domain.dialog.Dialog.TurnTimeExceed.TITLE),
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
                        text = stringResource(id = gomoku.domain.dialog.Dialog.TurnTimeExceed.BODY_MSG),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    SubmitButton(
                        backgroundColor = LightOrange,
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        onButtonText = stringResource(id = gomoku.domain.dialog.Dialog.TurnTimeExceed.BUTTON_MSG),
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
    TurnTimeExceedDialog()
}

