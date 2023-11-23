package gomoku.variant

import android.util.Log
import gomoku.game.domain.board.BoardSize
import gomoku.variant.domain.OpeningRule
import gomoku.variant.domain.VariantConfig
import gomoku.variant.domain.VariantName
import kotlinx.coroutines.delay

interface VariantService {
    suspend fun fetchVariants(): List<VariantConfig>
}

class FetchVariantsException(message: String, cause: Throwable? = null) : Exception(message, cause)

class FakeVariantService : VariantService {
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