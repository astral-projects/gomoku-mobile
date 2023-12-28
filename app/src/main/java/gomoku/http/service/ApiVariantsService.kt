package gomoku.http.service

import gomoku.domain.game.board.BoardSize
import gomoku.domain.service.variant.VariantService
import gomoku.domain.variant.OpeningRule
import gomoku.domain.variant.VariantConfig
import gomoku.domain.variant.VariantName
import gomoku.http.media.siren.SirenModel
import gomoku.http.models.games.VariantOutputModel
import gomoku.http.utils.callApi
import gomoku.http.utils.recipes.findRecipeUri
import gomoku.infrastructure.PreferencesDataStore

class ApiVariantsService(
    val preferences: PreferencesDataStore,
) : VariantService {
    override suspend fun fetchVariants(): List<VariantConfig> {
        val uri = findRecipeUri(preferences, "variants")
        val result: SirenModel<List<VariantOutputModel>, Unit> =
            callApi<Unit, SirenModel<List<VariantOutputModel>, Unit>>(url = uri)
        return result.properties.map { variant ->
            VariantConfig(
                id = variant.id.value,
                name = variant.name.toVariantName(),
                openingRule = variant.openingRule.toOpeningRule(),
                boardSize = variant.boardSize.toBoardSize(),
            )
        }
    }

    private fun String.toVariantName(): VariantName {
        return when (this) {
            "FREESTYLE" -> VariantName.FREESTYLE
            "OMOK" -> VariantName.OMOK
            "RENJU" -> VariantName.RENJU
            "CARO" -> VariantName.CARO
            "NINUKI_RENJU" -> VariantName.NINUKI_RENJU
            "PENTE" -> VariantName.PENTE
            else -> throw IllegalArgumentException("Unknown variant name: $this")
        }
    }

    private fun String.toOpeningRule(): OpeningRule {
        return when (this) {
            "LONG_PRO" -> OpeningRule.LONG_PRO
            "PRO" -> OpeningRule.PRO
            "NONE" -> OpeningRule.LONG_PRO
            else -> throw IllegalArgumentException("Unknown opening rule: $this")
        }
    }

    private fun String.toBoardSize(): BoardSize {
        return when (this) {
            "FIFTEEN" -> BoardSize.FIFTEEN
            "NINETEEN" -> BoardSize.NINETEEN
            else -> throw IllegalArgumentException("Unknown board size: $this")
        }
    }
}