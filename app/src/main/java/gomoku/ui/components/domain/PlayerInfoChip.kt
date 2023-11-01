package gomoku.ui.components.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.components.generic.CustomIconChip
import gomoku.ui.containers.PlayerInfo
import pdm.gomoku.R

@Composable
fun PlayerInfoChip(
    playerInfo: PlayerInfo,
    trailingIconId: Int,
    select: Boolean
) {
    CustomIconChip(
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
