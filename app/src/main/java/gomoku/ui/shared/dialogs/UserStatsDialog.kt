package gomoku.ui.shared.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import gomoku.domain.leaderboard.NumberFormatter
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.domain.leaderboard.RankingInfo
import gomoku.ui.shared.background.BackgroundConfig
import gomoku.ui.shared.theme.GomokuTheme
import pdm.gomoku.R

// Config
private const val DIALOG_WIDTH_FACTOR = 0.9f
private const val DIALOG_HEIGHT_FACTOR = 0.55f
private val interiorDialogPadding = 5.dp
private val borderSize = 2.dp
private val verticalSpaceBetweenTexts = (-7).dp
private val horizontalSpaceBetweenTexts = 5.dp
private val dialogCornerShapeSize = 55.dp
private val iconProfileSize = 150.dp
private val leftRightPadding = 15.dp
private val topPadding = 40.dp
private val bottomPadding = 30.dp
private val topTextPadding = 10.dp
private val bottomSymbolPadding = 10.dp

/**
 * Dialog that will be shown when the user clicks on the profile icon.
 * @param background configuration of the background.
 * @param userRankingInfo ranking info of the user.
 * @param onDismissRequest callback to be invoked when the user wants to dismiss the dialog.
 */
@Composable
fun UserStatsDialog(
    background: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    userRankingInfo: RankingInfo,
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
                .background(MaterialTheme.colorScheme.secondary)
                .clickable { onDismissRequest() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Interior section
            Box(
                modifier = Modifier
                    .padding(interiorDialogPadding)
                    .border(borderSize, MaterialTheme.colorScheme.outline, cornerShape)
                    .background(MaterialTheme.colorScheme.primary),
            ) {
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
                    Row {
                        DisplayPlayerInfoOnProfile(
                            playerInfo = userRankingInfo.playerInfo,
                            modifier = Modifier
                                .size(iconProfileSize)
                        )
                    }
                    DisplayRankingInfo(gamesInfo = userRankingInfo)
                }

            }
        }
    }
}

@Composable
fun DisplayRankingInfo(gamesInfo: RankingInfo) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(horizontalSpaceBetweenTexts),
    ) {
        // games
        GameStat(
            amount = gamesInfo.playedGames.toString(),
            component = stringResource(id = gomoku.domain.dialog.Dialog.UserStats.GAMES),
            iconId = R.drawable.game_controller,
            iconDescription = "Player Games"
        )
        // wins
        GameStat(
            amount = gamesInfo.wins.toString(),
            component = stringResource(id = gomoku.domain.dialog.Dialog.UserStats.WINS),
            iconId = R.drawable.win_badge,
            iconDescription = "Game Wins"
        )
        // losses
        GameStat(
            amount = gamesInfo.losses.toString(),
            component = stringResource(id = gomoku.domain.dialog.Dialog.UserStats.LOSSES),
            iconId = R.drawable.lost,
            iconDescription = "Games Lost"
        )
        // draws
        GameStat(
            amount = gamesInfo.draws.toString(),
            component = stringResource(id = gomoku.domain.dialog.Dialog.UserStats.DRAWS),
            iconId = R.drawable.handshake,
            iconDescription = "Game Draws"
        )
    }
}

@Composable
fun DisplayPlayerInfoOnProfile(playerInfo: PlayerInfo, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Image(
            painterResource(id = playerInfo.iconId),
            contentDescription = "User photo",
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(topTextPadding))
        Text(
            text = playerInfo.name,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
fun GameStat(amount: String, component: String, iconId: Int, iconDescription: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TextTitle(text = amount, value = component)
        Spacer(modifier = Modifier.height(bottomSymbolPadding))
        Image(
            painterResource(iconId),
            contentDescription = iconDescription,
            modifier = Modifier
        )
    }
}

@Composable
fun TextTitle(text: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(verticalSpaceBetweenTexts)
    ) {
        Text(
            text = NumberFormatter.format(text),
            fontWeight = FontWeight.SemiBold,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
        )
        Text(text = value)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileDialogPreview() {
    GomokuTheme {
        UserStatsDialog(
            userRankingInfo = RankingInfo(
                id = 1,
                playerInfo = PlayerInfo(
                    id = 3,
                    name = "Geralt of Rivia",
                    iconId = R.drawable.man,
                ),
                rank = 1,
                points = 1000,
                playedGames = 329,
                wins = 300,
                losses = 499,
                draws = 230
            ),
            onDismissRequest = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileDialogPreviewWithALotOfGames() {
    GomokuTheme {
        UserStatsDialog(
            userRankingInfo = RankingInfo(
                id = 1,
                playerInfo = PlayerInfo(
                    id = 4,
                    name = "Geralt of Rivia and more text so we see that is working correctly",
                    iconId = R.drawable.man,
                ),
                rank = 1,
                points = 1000,
                playedGames = 3291212,
                wins = 3033,
                losses = 493219,
                draws = 2331230
            ),
            onDismissRequest = {}
        )
    }
}