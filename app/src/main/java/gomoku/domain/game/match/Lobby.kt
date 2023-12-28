package gomoku.domain.game.match

import gomoku.domain.login.UserInfo

data class Lobby(
    val id: Int,
    val host: UserInfo,
    val variantId: Int,
) : Match()
