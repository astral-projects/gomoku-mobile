package gomoku.ui.shared.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
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
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.ui.shared.background.BackgroundConfig
import gomoku.ui.shared.theme.GomokuTheme
import pdm.gomoku.R

// Config
private val dialogRoundCornerShapeSize = 20.dp
private const val DIALOG_WIDTH_FACTOR = 0.9f
private val headerIconSize = 40.dp
private val playerInfoSpacerHeight = 4.dp
private val iconPointsSpacerWidth = 15.dp
private val pointsTextSpacerWidth = 5.dp
private val iconPointsSpacerHeight = 20.dp
private val headerPadding = 8.dp
private val bottomPadding = 12.dp
private val borderWidth = 2.dp
val titleIconSpacerHeight = 8.dp
private val playerTextOffsetPadding = 30.dp

/**
 * Dialog that will be shown when the game is finished with the respective results.
 * @param backgroundConfig configuration of the background.
 * @param winnerInfo information of the winner.
 * @param loserInfo information of the loser.
 * @param winnerPoints points of the winner.
 * @param loserPoints points of the loser.
 * @param onDismissRequest callback to be invoked when the user wants to dismiss the dialog.
 */
@Composable
fun GameResultsDialog(
    backgroundConfig: BackgroundConfig = BackgroundConfig(
        LocalConfiguration.current
    ),
    winnerInfo: PlayerInfo,
    loserInfo: PlayerInfo,
    winnerPoints: Int,
    loserPoints: Int,
    onDismissRequest: () -> Unit,
) {
    val screenWidth = backgroundConfig.screenWidth
    val cornerShape = RoundedCornerShape(dialogRoundCornerShapeSize)
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .width(screenWidth * DIALOG_WIDTH_FACTOR)
                .clip(cornerShape)
                .border(borderWidth, MaterialTheme.colorScheme.outline, cornerShape)
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Top section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(borderWidth, MaterialTheme.colorScheme.outline, cornerShape)
                    .padding(headerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DisplayPlayerInfo(winnerInfo)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        BodyTitle(text = stringResource(id = gomoku.domain.dialog.Dialog.GameResults.TITLE))
                        Spacer(modifier = Modifier.height(titleIconSpacerHeight))
                        Image(
                            painterResource(id = R.drawable.checklist),
                            contentDescription = null
                        )
                    }
                    DisplayPlayerInfo(loserInfo)
                }
            }
            // Bottom section
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = bottomPadding)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottomPadding)
                ) {
                    // Winner match points
                    DisplayWinnerMatchPoints(points = winnerPoints)
                    IconPointsSpacer()
                    // Body Titles
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BodyTitle(text = stringResource(id = gomoku.domain.dialog.Dialog.GameResults.BODY_TEXT_TOP))
                        IconHeightSpacer()
                        BodyTitle(text = stringResource(id = gomoku.domain.dialog.Dialog.GameResults.BODY_TEXT_BOTTOM))
                    }
                    IconPointsSpacer()
                    // Loser match points
                    DisplayLoserMatchPoints(points = loserPoints)
                }
            }
        }
    }
}

@Composable
private fun IconPointsSpacer() =
    Spacer(modifier = Modifier.width(iconPointsSpacerWidth))

@Composable
fun BodyTitle(text: String, modifier: Modifier = Modifier) =
    Text(text = text, style = MaterialTheme.typography.titleMedium, modifier = modifier)

@Composable
private fun DisplayWinnerMatchPoints(points: Int) =
    DisplayMatchPointsContainer(
        points = points,
        isWinner = true
    )

@Composable
private fun DisplayLoserMatchPoints(points: Int) =
    DisplayMatchPointsContainer(
        points = points,
        isWinner = false
    )

@Composable
private fun DisplayMatchPointsContainer(points: Int, isWinner: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        DisplayMatchPoints(points = points, isWinner = isWinner)
    }
}

@Composable
fun DisplayPlayerInfo(playerInfo: PlayerInfo, modifier: Modifier = Modifier) =
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        // Used to avoid the text to grow indefinitely
        modifier = modifier.requiredWidth(headerIconSize + playerTextOffsetPadding)
    ) {
        Image(
            painterResource(id = playerInfo.iconId),
            contentDescription = null,
            modifier = modifier.size(headerIconSize)
        )
        Spacer(modifier = modifier.height(playerInfoSpacerHeight))
        Text(
            text = playerInfo.name,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }

@Composable
private fun DisplayMatchPoints(points: Int, isWinner: Boolean) =
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Image(
            painterResource(id = if (isWinner) R.drawable.check_mark_2 else R.drawable.close),
            contentDescription = null
        )
        IconHeightSpacer()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$points", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(pointsTextSpacerWidth))
            Image(
                painterResource(id = R.drawable.coins),
                contentDescription = null
            )
        }
    }

@Composable
private fun IconHeightSpacer() = Spacer(modifier = Modifier.height(iconPointsSpacerHeight))

@Preview(showBackground = true)
@Composable
fun GameResultsDialog3() {
    GomokuTheme {
        GameResultsDialog(
            winnerInfo = PlayerInfo(4, "Host Player", R.drawable.man),
            loserInfo = PlayerInfo(7, "Guest Player", R.drawable.woman),
            winnerPoints = 250,
            loserPoints = 100,
            onDismissRequest = {}
        )
    }
}