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
            VariantName.FREESTYLE,
            OpeningRule.PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
            VariantName.RENJU,
            OpeningRule.PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
            VariantName.CARO,
            OpeningRule.PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
            VariantName.OMOK,
            OpeningRule.PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
            VariantName.NINUKI_RENJU,
            OpeningRule.PRO,
            BoardSize.FIFTEEN
        ),
        VariantConfig(
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