package gomoku.ui.variant.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import gomoku.domain.variant.Variant
import gomoku.domain.variant.VariantConfig
import gomoku.ui.shared.components.ContentNotFound
import gomoku.ui.shared.components.ExpandableCard
import pdm.gomoku.R

// Config
private const val CARD_WEIGHT = 0.85f
private const val BODY_HEIGHT_FACTOR = 0.55f
private val variantBodyVerticalPadding = 15.dp
private val variantItemVerticalPadding = 6.dp
private val radioButtonVariantItemSpacerWidth = 6.dp

@Composable
fun VariantTable(
    variants: List<VariantConfig>,
    selectedOption: VariantConfig?,
    onOptionSelected: (VariantConfig?) -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            // used to allow footer to not be pushed to the bottom
            .fillMaxHeight(BODY_HEIGHT_FACTOR)
            .padding(vertical = variantBodyVerticalPadding)
    ) {
        if (variants.isEmpty()) {
            item {
                ContentNotFound(text = stringResource(Variant.noVariantsFound))
            }
        } else {
            variants.forEach { variant ->
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = variantItemVerticalPadding)
                    ) {
                        RadioButton(
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.outline),
                            selected = (selectedOption == variant),
                            onClick = { onOptionSelected(variant) },
                            modifier = Modifier
                                .weight(1f - CARD_WEIGHT)
                                .testTag(variant.name.name)
                        )
                        Spacer(modifier = Modifier.width(radioButtonVariantItemSpacerWidth))
                        ExpandableCard(
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            leadingIconId = R.drawable.rule,
                            modifier = Modifier.weight(CARD_WEIGHT),
                            title = variant.name.name,
                            description = getCardDescriptionFormat(variant),
                            arrowColor = MaterialTheme.colorScheme.tertiary,
                        )
                    }
                }
            }
        }
    }
}

/**
 * Returns the description format for the given [VariantConfig].
 * @param variantConfig the [VariantConfig] to get the description format for.
 */
@Composable
private fun getCardDescriptionFormat(variantConfig: VariantConfig): String {
    val delimiter = ": "
    val boardSize = stringResource(id = Variant.boardSize)
    val openingRule = stringResource(id = Variant.openingRule)
    return listOf(
        "$boardSize$delimiter${variantConfig.boardSize.value}x${variantConfig.boardSize.value}",
        "$openingRule$delimiter${variantConfig.openingRule.name}"
    ).joinToString("\n")
}