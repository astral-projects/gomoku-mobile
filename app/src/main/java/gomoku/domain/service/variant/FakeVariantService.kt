package gomoku.domain.service.variant

import gomoku.domain.service.AbstractFakeService
import gomoku.domain.variant.VariantConfig
import kotlinx.coroutines.delay

object FakeVariantService : VariantService, AbstractFakeService() {

    override suspend fun fetchVariants(): List<VariantConfig> {
        delay(fetchDelay)
        return variants
    }
}