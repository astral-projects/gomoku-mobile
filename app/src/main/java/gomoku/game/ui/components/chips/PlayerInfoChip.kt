package gomoku.game.ui.components.chips

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.CustomIconChip
import pdm.gomoku.R

// Config
private const val CHIP_HEIGHT_FACTOR = 0.3f

/**
 * A [CustomIconChip] that displays player information.
 * @param backgroundConfig The background configuration to be used.
 * @param playerInfo The [PlayerInfo] to display.
 * @param trailingIconId The id of the trailing icon.
 * @param select The select state of the chip.
 */
@Composable
fun PlayerInfoChip(
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    playerInfo: PlayerInfo,
    trailingIconId: Int,
    select: Boolean
) {
    CustomIconChip(
        textModifier = Modifier.width(backgroundConfig.screenWidth * CHIP_HEIGHT_FACTOR),
        label = playerInfo.name,
        select = select,
        leadingIconId = playerInfo.iconId,
        trailingIconId = trailingIconId
    )
}

@Composable
@Preview
private fun PlayerInfoChipPreview() {
    PlayerInfoChip(
        playerInfo = PlayerInfo(
            name = "Player 1",
            iconId = R.drawable.man
        ),
        trailingIconId = R.drawable.check_mark,
        select = true
    )
}
