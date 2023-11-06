package gomoku.about.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.about.domain.About
import gomoku.about.domain.Section
import gomoku.about.ui.components.FooterLogo
import gomoku.home.domain.Home.GAME_NAME
import gomoku.ui.background.Background
import gomoku.ui.components.ExpandableCard
import gomoku.ui.components.TopNavHeader
import gomoku.ui.theme.GomokuTheme

// Config
private val sectionVerticalPadding = 10.dp
private val sectionPadding = 10.dp

/**
 * Represents the About screen main composable.
 * @param onBurgerMenuClick the callback to be called when the burger menu is clicked.
 * @param sections the [List] of [Section] to be displayed in the body.
 */
@Composable
fun AboutScreen(
    onBurgerMenuClick: () -> Unit,
    sections: List<Section>
) {
    GomokuTheme {
        Background(
            header = { TopNavHeader(title = GAME_NAME, onBurgerMenuClick = onBurgerMenuClick) },
            useBodySurface = false,
            footer = { FooterLogo() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(sectionPadding)
            ) {
                sections.forEach { section ->
                    Row(
                        modifier = Modifier
                            .padding(vertical = sectionVerticalPadding)
                    ) {
                        ExpandableCard(
                            arrowColor = MaterialTheme.colorScheme.onSecondary,
                            titleColor = MaterialTheme.colorScheme.onSecondary,
                            leadingIconId = section.iconId,
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            title = section.title.value,
                            description = section.description.value
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun AboutScreenPreview() {
    AboutScreen(
        onBurgerMenuClick = {},
        sections = About.sections
    )
}