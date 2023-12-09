package gomoku.shared.popups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.leaderboard.domain.RankingInfo
import gomoku.shared.background.BackgroundConfig
import gomoku.shared.theme.GomokuTheme
import pdm.gomoku.R

const val POPUP_INTERIOR_SIZE_FACTOR = 0.9f


@Composable
fun ProfilePopup(
    background: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    rankingInfo: RankingInfo,
    onDismissRequest: () -> Unit = {},

    ) {
    DomainPopup(
        onDismissRequest = onDismissRequest
    ) {
        val screenWidth = background.screenWidth
        val screenHeight = background.screenHeight
        val cornerShape = RoundedCornerShape(popupRoundCornerShapeSize)
        val externalPopupWidth = screenWidth * POPUP_WIDTH_FACTOR
        val externalPopupHeight = screenHeight * POPUP_HEIGHT_FACTOR

        Column(
            modifier = Modifier
                .width(externalPopupWidth)
                .height(externalPopupHeight)
                .clip(cornerShape)
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Interior section
            Box(
                modifier = Modifier
                    .width(externalPopupWidth * POPUP_INTERIOR_SIZE_FACTOR)
                    .height(externalPopupHeight * POPUP_INTERIOR_SIZE_FACTOR)
                    .clip(cornerShape)
                    .background(MaterialTheme.colorScheme.primary),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    DisplayPlayerInfo(playerInfo = rankingInfo.playerInfo)
                    DisplayGamesInfo(gamesInfo = rankingInfo)
                }

            }
        }

    }
}

@Composable
fun DisplayGamesInfo(gamesInfo: RankingInfo) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // played games
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            BodyTitle(text = gamesInfo.playedGames.toString())
            Text(text = "Games")
            // wins
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                BodyTitle(text = gamesInfo.wins.toString())
                Text(text = "Wins")
                // losses
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    BodyTitle(text = gamesInfo.losses.toString())
                    Text(text = "Losses")
                    // draws
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        BodyTitle(text = gamesInfo.draws.toString())
                        Text(text = "Draws")
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ProfilePopupPreview() {
    GomokuTheme {
        ProfilePopup(
            rankingInfo = RankingInfo(
                playerInfo = PlayerInfo(
                    name = "Geralt of Rivia",
                    iconId = R.drawable.man,
                ),
                rank = 1,
                points = 1000,
                playedGames = 329,
                wins = 56,
                losses = 89,
                draws = 6
            )
        )
    }
}

