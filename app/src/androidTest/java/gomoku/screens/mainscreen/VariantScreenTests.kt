package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import gomoku.game.domain.board.BoardSize
import gomoku.variant.domain.OpeningRule
import gomoku.variant.domain.Variant
import gomoku.variant.domain.VariantConfig
import gomoku.variant.domain.VariantName
import gomoku.variant.ui.VariantScreen
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class VariantScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun trying_to_click_on_the_navigation_variant_without_any_variant_selected() {
        // Arrange
        var variantRequested = false

        composeTestRule.setContent {
            VariantScreen(
                onSubmit = { _ ->
                    variantRequested = true
                },
                listOf(
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
                    )
                )
            )
        }
        // Act
        composeTestRule.onNodeWithTag(Variant.SUBMIT_BUTTON_TEXT).performClick()
        assertFalse(variantRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_variant_with_FreeStyle_variant_selected() {
        // Arrange
        var variantRequested = false

        composeTestRule.setContent {
            VariantScreen(
                onSubmit = { _ ->
                    variantRequested = true
                },
                listOf(
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
                    )
                )
            )
        }
        // Act
        composeTestRule.onNodeWithTag(VariantName.FREESTYLE.name).performClick()
        composeTestRule.onNodeWithTag(Variant.SUBMIT_BUTTON_TEXT).performClick()
        assertTrue(variantRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_variant_with_Renju_variant_selected() {
        // Arrange
        var variantRequested = false

        composeTestRule.setContent {
            VariantScreen(
                onSubmit = { _ ->
                    variantRequested = true
                },
                listOf(
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
                    )
                )
            )
        }
        // Act
        composeTestRule.onNodeWithTag(VariantName.RENJU.name).performClick()
        composeTestRule.onNodeWithTag(Variant.SUBMIT_BUTTON_TEXT).performClick()
        assertTrue(variantRequested)
    }


}