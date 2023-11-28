package gomoku.variant

import gomoku.variant.domain.VariantConfig

interface VariantsInfoRepositroy {
    suspend fun getVariantsInfo(): List<VariantConfig>?

    suspend fun updateVariantsInfo(variantsInfo: List<VariantConfig>)
}