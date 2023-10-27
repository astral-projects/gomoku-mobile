package gomoku.about

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gomoku.ui.GAME_NAME
import gomoku.ui.background.Background
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.domain.SmallerHeaderLogo
import gomoku.ui.components.generic.ExpandableCard
import gomoku.ui.components.generic.TopNavHeader
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
            val background = BackgroundConfig(LocalConfiguration.current)
            val yellowSquareHeight = background.screenHeight / 9
            val aboutTextSize: TextUnit = (
                    (yellowSquareHeight / 4.0f).value + (yellowSquareHeight / 8.0f).value
                    ).sp
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
                            title = "Game Information",
                            description = "Gomoku, also known as Five in a Row, is a classic board game that's easy to learn but challenging to master. The goal is simple: be the first player to get five of your stones in a row horizontally, vertically, or diagonally on the game board.",
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
                            title = "Feedback and Support",
                            description = "We value your input! If you have questions, suggestions, or run into any issues while using our app, please don't hesitate to reach out to our support team.",
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
                            title = "Authors",
                            description = "",
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