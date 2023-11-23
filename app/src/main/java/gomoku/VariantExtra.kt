package gomoku

import android.os.Parcelable
import gomoku.game.domain.board.BoardSize
import gomoku.variant.domain.OpeningRule
import gomoku.variant.domain.VariantConfig
import gomoku.variant.domain.VariantName
import kotlinx.parcelize.Parcelize


const val VARIANT_EXTRA = "Variant"

/**
 * Represents the data to be passed as an extra in the intent that navigates to the
 * [GameActivity]. We use this class because the [VariantInfo] class is not
 * parcelable and we do not want to make it parcelable because it's a domain class.
 */
@Parcelize
data class VariantExtra(
    val id: Int,
    val name: VariantName,
    val openingRule: OpeningRule,
    val boardSize: BoardSize
) :
    Parcelable {
    constructor(variantInfo: VariantConfig) : this(
        variantInfo.id,
        variantInfo.name,
        variantInfo.openingRule,
        variantInfo.boardSize
    )
}

fun VariantExtra.toVariantInfo() = VariantConfig(id, name, openingRule, boardSize)