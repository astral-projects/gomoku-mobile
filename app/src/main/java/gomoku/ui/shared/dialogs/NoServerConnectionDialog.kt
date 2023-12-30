package gomoku.ui.shared.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import gomoku.ui.shared.background.BackgroundConfig
import gomoku.ui.shared.components.DisplayDialogMessageWithImage
import pdm.gomoku.R

// Config
private const val DIALOG_WIDTH_FACTOR = 0.9f
private const val DIALOG_HEIGHT_FACTOR = 0.55f
private val borderSize = 2.dp
private val dialogCornerShapeSize = 55.dp
private val leftRightPadding = 15.dp
private val topPadding = 40.dp
private val bottomPadding = 30.dp

@Composable
fun NoServerConnectionDialog(
    background: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onDismissRequest: () -> Unit,
) {
    val screenWidth = background.screenWidth
    val screenHeight = background.screenHeight
    val cornerShape = RoundedCornerShape(dialogCornerShapeSize)
    val externalDialogWidth = screenWidth * DIALOG_WIDTH_FACTOR
    val externalDialogHeight = screenHeight * DIALOG_HEIGHT_FACTOR
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .width(externalDialogWidth)
                .height(externalDialogHeight)
                .clip(cornerShape)
                .border(borderSize, MaterialTheme.colorScheme.outline, cornerShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onDismissRequest() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Interior section
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = leftRightPadding,
                        end = leftRightPadding,
                        bottom = bottomPadding,
                        top = topPadding
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    DisplayDialogMessageWithImage(
                        modifier = Modifier,
                        message = stringResource(id = R.string.no_server_connection),
                        imageId = R.drawable.no_server_connection
                    )
                    Text(
                        text = stringResource(id = R.string.no_server_connection_detail),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun NoServerConnectionDialogPreview() {
    NoServerConnectionDialog(
        onDismissRequest = {}
    )
}
