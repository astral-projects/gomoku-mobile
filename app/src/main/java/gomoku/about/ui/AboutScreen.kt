package gomoku.about.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gomoku.about.domain.About
import gomoku.home.domain.Home.GAME_NAME
import gomoku.ui.background.Background
import gomoku.ui.components.SmallerHeaderLogo
import gomoku.ui.components.ExpandableCard
import gomoku.ui.components.TopNavHeader
import gomoku.ui.theme.GomokuTheme
import gomoku.ui.theme.PaynesGrey

private val surfaceCornerShapeSize: Dp = 35.dp
private val bodyPaddingHorizontal: Dp = 15.dp
private val bodyPaddingVertical: Dp = 15.dp

@Composable
fun AboutScreen(
    onBurgerMenuClick: () -> Unit,
) {
    GomokuTheme {
        Background(
            header = {
                TopNavHeader(title = GAME_NAME, onBurgerMenuClick = onBurgerMenuClick)
            },
            useBodySurface = false,
            footer = {
                SmallerHeaderLogo()
            }

        ) {
            val cornerShapeSize = 30.dp

            Column(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = cornerShapeSize,
                            bottomStart = cornerShapeSize
                        )
                    )
                    .background(PaynesGrey)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(surfaceCornerShapeSize))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(
                                horizontal = bodyPaddingHorizontal,
                                vertical = bodyPaddingVertical
                            )
                    ) {
                        ExpandableCard(
                            title = About.gameInfo.first.value,
                            description = About.gameInfo.second.value,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(surfaceCornerShapeSize))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(
                                horizontal = bodyPaddingHorizontal,
                                vertical = bodyPaddingVertical
                            )
                    ) {
                        ExpandableCard(
                            title = About.support.first.value,
                            description = About.support.second.value,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(surfaceCornerShapeSize))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(
                                horizontal = bodyPaddingHorizontal,
                                vertical = bodyPaddingVertical
                            )
                    ) {
                        ExpandableCard(
                            title = About.authors.first.value,
                            description = About.authors.second
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun BackGroundAboutPreview() {
    AboutScreen(onBurgerMenuClick = {})
}