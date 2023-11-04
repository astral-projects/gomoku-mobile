package gomoku.variant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.game.domain.board.BoardSize
import gomoku.home.domain.Home.GAME_NAME
import gomoku.register.components.FooterBubbles
import gomoku.ui.background.Background
import gomoku.ui.components.ExpandableCard
import gomoku.ui.components.Header
import gomoku.ui.components.SubmitButton
import gomoku.ui.theme.GomokuTheme
import gomoku.variant.domain.OpeningRule
import gomoku.variant.domain.Variant
import gomoku.variant.domain.VariantName
import pdm.gomoku.R

// Constants
private const val HEADER = "Variants"
private const val SUBMIT_TEXT = "Play"

// Config
private const val CARD_WEIGHT = 0.85f
private const val BODY_HEIGHT_FACTOR = 0.6f
private val variantHeaderSpacerWidth = 12.dp
private val variantBodyVerticalPadding = 15.dp
private val variantSurfaceVerticalPadding = 15.dp
private val variantItemVerticalPadding = 6.dp
private val radioButtonVariantItemSpacerWidth = 6.dp

@Composable
fun VariantScreen(
    onSubmit: (Variant) -> Unit,
    variants: List<Variant>
) {
    require(variants.isNotEmpty()) { "variants must not be empty" }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(variants.first()) }
    GomokuTheme {
        Background(
            header = { Header(text = GAME_NAME) },
            footer = { FooterBubbles() }
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = variantSurfaceVerticalPadding)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Header(text = HEADER)
                    Spacer(modifier = Modifier.width(variantHeaderSpacerWidth))
                    Image(
                        painter = painterResource(id = R.drawable.book),
                        contentScale = ContentScale.Inside,
                        contentDescription = null
                    )
                }
                val listState = rememberLazyListState()
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        // used to allow footer to not be pushed to the bottom
                        .fillMaxHeight(BODY_HEIGHT_FACTOR)
                        .padding(vertical = variantBodyVerticalPadding)
                ) {
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
                                    modifier = Modifier.weight(1f - CARD_WEIGHT)
                                )
                                Spacer(modifier = Modifier.width(radioButtonVariantItemSpacerWidth))
                                val boardSize = variant.boardSize.value
                                ExpandableCard(
                                    backgroundColor = MaterialTheme.colorScheme.primary,
                                    leadingIconId = R.drawable.rule,
                                    modifier = Modifier.weight(CARD_WEIGHT),
                                    title = variant.name.name,
                                    description =
                                    "Board size: $boardSize" + "x" + "$boardSize\n" +
                                            "Opening rule: ${variant.openingRule.name}",
                                    iconColor = MaterialTheme.colorScheme.tertiary,
                                )
                            }
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SubmitButton(
                        onButtonText = SUBMIT_TEXT,
                        onClick = { onSubmit(selectedOption) },
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun VariantScreenPreview() {
    VariantScreen(
        onSubmit = {},
        variants = listOf(
            Variant(
                name = VariantName.FREESTYLE,
                boardSize = BoardSize.FIFTEEN,
                openingRule = OpeningRule.PRO
            ),
            Variant(
                name = VariantName.OMOK,
                boardSize = BoardSize.NINETEEN,
                openingRule = OpeningRule.LONG_PRO
            ),
            Variant(
                name = VariantName.PENTE,
                boardSize = BoardSize.FIFTEEN,
                openingRule = OpeningRule.PRO
            ),
            Variant(
                name = VariantName.RENJU,
                boardSize = BoardSize.FIFTEEN,
                openingRule = OpeningRule.PRO
            ),
            Variant(
                name = VariantName.CARO,
                boardSize = BoardSize.NINETEEN,
                openingRule = OpeningRule.PRO
            ),
        )
    )
}