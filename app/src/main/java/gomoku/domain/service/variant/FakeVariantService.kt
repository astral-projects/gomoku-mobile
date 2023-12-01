package gomoku.domain.service.variant

import android.util.Log
import gomoku.domain.game.board.BoardSize
import gomoku.domain.variant.OpeningRule
import gomoku.domain.variant.VariantConfig
import gomoku.domain.variant.VariantName
import kotlinx.coroutines.delay

object FakeVariantService : VariantService {
    private val variants = listOf(
        VariantConfig(
            1,
            VariantName.FREESTYLE,
            OpeningRule.PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
            2,
            VariantName.RENJU,
            OpeningRule.PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
            3,
            VariantName.CARO,
            OpeningRule.PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
            4,
            VariantName.OMOK,
            OpeningRule.PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
            5,
            VariantName.NINUKI_RENJU,
            OpeningRule.PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
            6,
            VariantName.PENTE,
            OpeningRule.PRO,
            BoardSize.FIFTEEN
        )
    )

    override suspend fun fetchVariants(): List<VariantConfig> {
        Log.v("Variants", "fetching variants...")
        delay(5000)
        Log.v("Variants", "fetched variants")
        return variants
    }
}