package gomoku.screens.mainscreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import gomoku.game.domain.board.BoardSize
import gomoku.shared.components.BurgerMenuButton
import gomoku.shared.components.navigation.BurgerMenuAboutButton
import gomoku.shared.components.navigation.BurgerMenuFindGameButton
import gomoku.shared.components.navigation.BurgerMenuLogoutButton
import gomoku.shared.components.navigation.BurgerMenuSwitchThemeButton
import gomoku.variant.domain.OpeningRule
import gomoku.variant.domain.VariantConfig
import gomoku.variant.domain.VariantName
import gomoku.variant.ui.VariantScreen
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

const val variantSubmitButtonText = "Play"

class VariantScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    val variants = listOf(
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

    @Test
    fun trying_to_click_on_the_navigation_variant_without_any_variant_selected() {
        // Arrange
        var variantRequested = false
        composeTestRule.setContent {
            VariantScreen(
                onSubmit = { _ ->
                    variantRequested = true
                },
                variants = variants,
                toLeaderboardScreen = {},
                toAboutScreen = {},
                onLogoutRequest = {}
            )
        }
        // Act
        composeTestRule.onNodeWithTag(variantSubmitButtonText).performClick()
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
                variants = variants,
                toLeaderboardScreen = {},
                toAboutScreen = {},
                onLogoutRequest = {}
            )
        }
        // Act
        composeTestRule.onNodeWithTag(VariantName.FREESTYLE.name).performClick()
        composeTestRule.onNodeWithTag(variantSubmitButtonText).performClick()
        // Assert
        assertTrue(variantRequested)
    }

    @Test
    fun trying_to_click_on_the_navigation_variant_with_Pente_variant_selected() {
        // Arrange
        var variantRequested = false

        composeTestRule.setContent {
            VariantScreen(
                onSubmit = { _ ->
                    variantRequested = true
                },
                variants = variants,
                toLeaderboardScreen = {},
                toAboutScreen = {},
                onLogoutRequest = {}
            )
        }
        // Act
        composeTestRule.onNodeWithTag(VariantName.PENTE.name).performClick()
        composeTestRule.onNodeWithTag(variantSubmitButtonText).performClick()
        assertTrue(variantRequested)
    }


    @Test
    fun try_to_click_on_burger_menu_and_check_if_it_opens() {
        //Arrange
        composeTestRule.setContent {
            VariantScreen(
                onSubmit = { _ -> },
                variants = variants,
                toLeaderboardScreen = {},
                toAboutScreen = {},
                onLogoutRequest = {}
            )

        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuButton).performClick()
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_leaderboard() {
        var leaderboardWasCalled = false

        composeTestRule.setContent {
            VariantScreen(
                onSubmit = { _ -> },
                variants = variants,
                toLeaderboardScreen = { leaderboardWasCalled = true },
                toAboutScreen = {},
                onLogoutRequest = {}
            )

        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuFindGameButton).performClick()

        //Assert
        assertFalse(leaderboardWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_about() {
        var aboutWasCalled = false

        composeTestRule.setContent {
            VariantScreen(
                onSubmit = { _ -> },
                variants = variants,
                toLeaderboardScreen = {},
                toAboutScreen = { aboutWasCalled = true },
                onLogoutRequest = {}
            )

        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuAboutButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuAboutButton).performClick()

        //Assert
        assertTrue(aboutWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_navigate_to_logout() {
        var logoutWasCalled = false

        composeTestRule.setContent {
            VariantScreen(
                onSubmit = { _ -> },
                variants = variants,
                toLeaderboardScreen = {},
                toAboutScreen = {},
                onLogoutRequest = { logoutWasCalled = true }
            )

        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuLogoutButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuLogoutButton).performClick()

        //Assert
        assertTrue(logoutWasCalled)
    }

    @Test
    fun on_burger_menu_try_to_switch_theme() {
        //Arrange
        composeTestRule.setContent {
            VariantScreen(
                onSubmit = { _ -> },
                variants = variants,
                toLeaderboardScreen = {},
                toAboutScreen = {},
                onLogoutRequest = {}
            )
        }

        //Act
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).assertExists()
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).performClick()

        //Assert
        composeTestRule.onNodeWithTag(BurgerMenuSwitchThemeButton).assertExists()

    }

}