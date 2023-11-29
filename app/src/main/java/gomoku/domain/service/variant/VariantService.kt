package gomoku.domain.service.variant

import gomoku.domain.variant.VariantConfig

interface VariantService {
    suspend fun fetchVariants(): List<VariantConfig>
}