package gomoku.http

import gomoku.variant.VariantService
import gomoku.variant.domain.VariantConfig

class VariantServiceImplementation(
    private val variantsProvider: List<VariantService>
) : VariantService {

    override suspend fun fetchVariants(): List<VariantConfig> {
        val index = variantsProvider.indices.random()
        return variantsProvider[index].fetchVariants()
    }
}