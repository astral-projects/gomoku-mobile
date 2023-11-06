package gomoku.variant.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import gomoku.register.ui.components.FooterBubbles
import gomoku.ui.background.Background
import gomoku.ui.components.HeaderText
import gomoku.ui.components.HeadlineText
import gomoku.ui.components.SubmitButton
import gomoku.ui.theme.GomokuTheme
import gomoku.variant.domain.OpeningRule
import gomoku.variant.domain.Variant
import gomoku.variant.domain.VariantConfig
import gomoku.variant.domain.VariantName
import gomoku.variant.ui.components.VariantTable
import pdm.gomoku.R

// Config
private val variantHeaderSpacerWidth = 12.dp
private val variantSurfaceVerticalPadding = 15.dp

/**
 * Represents the Variant screen main composable.
 * @param onSubmit the callback to be called when the submit button is clicked.
 * @param variants the [List] of [VariantConfig]s to be displayed in the body.
 */
@Composable
fun VariantScreen(
    onSubmit: (VariantConfig) -> Unit = {},
    variants: List<VariantConfig>
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf<VariantConfig?>(null) }
    GomokuTheme {
        Background(
            header = { HeadlineText(text = GAME_NAME) },
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
                    HeaderText(text = Variant.TITLE)
                    Spacer(modifier = Modifier.width(variantHeaderSpacerWidth))
                    Image(
                        painter = painterResource(id = R.drawable.book),
                        contentScale = ContentScale.Inside,
                        contentDescription = null
                    )
                }
                VariantTable(
                    variants = variants,
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SubmitButton(
                        enable = selectedOption != null,
                        onButtonText = Variant.SUBMIT_BUTTON_TEXT,
                        onClick = { selectedOption?.let { onSubmit(it) } }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun VariantScreenPreview() {
    VariantScreen(
        variants = listOf(
            VariantConfig(
                name = VariantName.FREESTYLE,
                boardSize = BoardSize.FIFTEEN,
                openingRule = OpeningRule.PRO
            ),
            VariantConfig(
                name = VariantName.OMOK,
                boardSize = BoardSize.NINETEEN,
                openingRule = OpeningRule.LONG_PRO
            ),
            VariantConfig(
                name = VariantName.PENTE,
                boardSize = BoardSize.FIFTEEN,
                openingRule = OpeningRule.PRO
            ),
            VariantConfig(
                name = VariantName.RENJU,
                boardSize = BoardSize.FIFTEEN,
                openingRule = OpeningRule.PRO
            ),
            VariantConfig(
                name = VariantName.CARO,
                boardSize = BoardSize.NINETEEN,
                openingRule = OpeningRule.PRO
            ),
        )
    )
}

@Composable
@Preview
private fun VariantScreenEmptyPreview() {
    VariantScreen(variants = emptyList())
}