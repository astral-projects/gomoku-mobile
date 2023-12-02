package gomoku.domain.service.variant

import gomoku.domain.service.variant.errors.FetchVariantsException
import gomoku.domain.variant.VariantConfig

/**
 * Defines the behavior of a variant service.
 */
interface VariantService {

    /**
     * Retrieves a list of available game variants.
     * @return A list of available game variants.
     * @throws FetchVariantsException if the variants could not be fetched.
     */
    @Throws(FetchVariantsException::class)
    suspend fun fetchVariants(): List<VariantConfig>
}
