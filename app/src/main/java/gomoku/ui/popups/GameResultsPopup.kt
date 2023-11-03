package gomoku.ui.popups

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.containers.PlayerInfo
import pdm.gomoku.R

// Constants
private const val TITLE = "Results"
private const val BODY_TEXT_TOP = "Winner"
private const val BODY_TEXT_BOTTOM = "Points"

// Config
private val HeaderIconSize = 40.dp
private val PlayerInfoSpacerHeight = 4.dp
private val IconPointsSpacerWidth = 15.dp
private val PointsTextSpacerWidth = 5.dp
private val IconPointsSpacerHeight = 20.dp
private val HeaderPadding = 8.dp
private val BottomPadding = 12.dp
private val BorderWidth = 2.dp
private val TitleIconSpacerHeight = 8.dp
private val PlayerTextOffsetPadding = 30.dp

/**
 * Pop up that will be shown when the game is finished with the game results.
 * @param backgroundConfig configuration of the background.
 * @param winnerInfo information of the winner.
 * @param loserInfo information of the loser.
 * @param winnerPoints points of the winner.
 * @param loserPoints points of the loser.
 */
@Composable
fun GameResultsPopup(
    backgroundConfig: BackgroundConfig = BackgroundConfig(
        LocalConfiguration.current
    ),
    winnerInfo: PlayerInfo,
    loserInfo: PlayerInfo,
    winnerPoints: Int,
    loserPoints: Int,
    onDismissRequest: () -> Unit,
) {
    DomainPopup(onDismissRequest = onDismissRequest) {
        val screenWidth = backgroundConfig.screenWidth
        val cornerShape = RoundedCornerShape(PopupRoundCornerShapeSize)
        Column(
            modifier = Modifier
                .clip(cornerShape)
                .width(screenWidth * PopupWidthFactor)
                .border(BorderWidth, MaterialTheme.colorScheme.outline, cornerShape)
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Top section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderWidth, MaterialTheme.colorScheme.outline, cornerShape)
                    .padding(HeaderPadding),
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
                        BodyTitle(text = TITLE)
                        Spacer(modifier = Modifier.height(TitleIconSpacerHeight))
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
                    .padding(bottom = BottomPadding)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(BottomPadding)
                ) {
                    // Winner match points
                    DisplayWinnerMatchPoints(points = winnerPoints)
                    IconPointsSpacer()
                    // Body Titles
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BodyTitle(text = BODY_TEXT_TOP)
                        IconHeightSpacer()
                        BodyTitle(text = BODY_TEXT_BOTTOM)
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
    Spacer(modifier = Modifier.width(IconPointsSpacerWidth))

@Composable
private fun BodyTitle(text: String) =
    Text(text = text, style = MaterialTheme.typography.titleMedium)

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
private fun DisplayPlayerInfo(playerInfo: PlayerInfo) =
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        // Used to avoid the text to grow indefinitely
        modifier = Modifier.requiredWidth(HeaderIconSize + PlayerTextOffsetPadding)
    ) {
        Image(
            painterResource(id = playerInfo.iconId),
            contentDescription = null,
            modifier = Modifier.size(HeaderIconSize)
        )
        Spacer(modifier = Modifier.height(PlayerInfoSpacerHeight))
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
            Spacer(modifier = Modifier.width(PointsTextSpacerWidth))
            Image(
                painterResource(id = R.drawable.coins),
                contentDescription = null
            )
        }
    }

@Composable
private fun IconHeightSpacer() = Spacer(
    modifier = Modifier.height(
        IconPointsSpacerHeight
    )
)

@Preview(showBackground = true)
@Composable
fun GameResultsPopup() {
    GameResultsPopup(
        winnerInfo = PlayerInfo("Host Player", R.drawable.man),
        loserInfo = PlayerInfo("Guest Player", R.drawable.woman),
        winnerPoints = 250,
        loserPoints = 100,
        onDismissRequest = {}
    )
}