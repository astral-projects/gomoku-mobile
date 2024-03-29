package gomoku.domain.service

import gomoku.domain.game.board.BoardSize
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.domain.login.UserInfo
import gomoku.domain.variant.OpeningRule
import gomoku.domain.variant.VariantConfig
import gomoku.domain.variant.VariantName
import pdm.gomoku.R

/**
 * Provides utilities for fake services.
 */
abstract class AbstractFakeService {

    /**
     * The delay to be used when fetching data to simulate a real service latency.
     */
    open val fetchDelay = 3000L

    /**
     * A list of available icons.
     */
    val availableIcons = listOf(
        R.drawable.man,
        R.drawable.man2,
        R.drawable.man3,
        R.drawable.man4,
        R.drawable.man5,
        R.drawable.woman,
        R.drawable.woman2,
        R.drawable.woman3,
        R.drawable.woman4,
        R.drawable.woman5
    )

    /**
     * The admin user.
     */
    val admin = UserInfo(
        id = 1,
        username = "admin",
        email = "admin",
        token = "admin@gmail.com",
        iconId = availableIcons.first()
    )

    /**
     * The guest user.
     */
    val guest = UserInfo(
        id = 2,
        username = "guest",
        email = "guest",
        token = "guest@gmail.com",
        iconId = availableIcons.last()
    )

    /**
     * A list of fake players.
     */
    val fakePlayers = listOf(
        PlayerInfo(1, "Hosea Matthews", R.drawable.man),
        PlayerInfo(2, "John Marston", R.drawable.man2),
        PlayerInfo(4, "Arthur Morgan", R.drawable.man3),
        PlayerInfo(5, "Dutch van der Linde", R.drawable.man4),
        PlayerInfo(6, "Bill Williamson", R.drawable.man5),
        PlayerInfo(7, "Abigail Roberts", R.drawable.woman),
        PlayerInfo(8, "Sadie Adler", R.drawable.woman2),
        PlayerInfo(9, "Karen Jones", R.drawable.woman3),
        PlayerInfo(23, "Mary-Beth Gaskill", R.drawable.woman4),
        PlayerInfo(22, "Tilly Jackson", R.drawable.woman5)
    )

    /**
     * A list of fake variants.
     */
    val variants = listOf(
        VariantConfig(
            1,
            VariantName.FREESTYLE,
            OpeningRule.PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
            2,
            VariantName.RENJU,
            OpeningRule.LONG_PRO,
            BoardSize.NINETEEN
        ),
        VariantConfig(
            3,
            VariantName.CARO,
            OpeningRule.LONG_PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
            4,
            VariantName.OMOK,
            OpeningRule.PRO,
            BoardSize.NINETEEN
        ),
        VariantConfig(
            5,
            VariantName.NINUKI_RENJU,
            OpeningRule.LONG_PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
            6,
            VariantName.PENTE,
            OpeningRule.PRO,
            BoardSize.NINETEEN
        )
    )

    /**
     * Finds the first element matching the given [predicate],
     * or throws the given [ex] if no such element was found.
     * @param ex The exception to throw if no element was found.
     * @param predicate The predicate to test elements against.
     * @return The first element matching the given [predicate].
     */
    @Throws(Exception::class)
    inline fun <T> Iterable<T>.findOrThrow(ex: Exception, predicate: (T) -> Boolean): T {
        for (element in this) if (predicate(element)) return element
        throw ex
    }
}
