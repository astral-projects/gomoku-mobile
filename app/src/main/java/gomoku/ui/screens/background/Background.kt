package gomoku.ui.screens.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gomoku.ui.theme.EggShell
import gomoku.ui.theme.GomokuTheme
import gomoku.ui.theme.PaynesGrey
import gomoku.ui.theme.SilverLakeBlue

private val surfaceCornerShapeSize: Dp = 35.dp
private val headerPaddingHorizontal: Dp = 20.dp
private val headerPaddingTop: Dp = 20.dp
private val headerPaddingBottom: Dp = 40.dp
private val bodySurfacePaddingHorizontal: Dp = 35.dp
private val bodySurfacePaddingVertical: Dp = 10.dp
private val footerPaddingHorizontal: Dp = 10.dp
private val footerPaddingVertical: Dp = 2.dp
private val bodyPaddingHorizontal: Dp = 15.dp
private val bodyPaddingVertical: Dp = 15.dp
private val bodyOffsetIntoHeader: Dp = (-30).dp

/**
 * A background that has a [header], [body], and optional [footer].
 * All content is centered and restricted to a maximum width and height.
 * Header can only go up to 2/5 of the screen height.
 * @param config The configuration of the screen.
 * @param header The content of the header.
 * @param backgroundColor The color of the background.
 * @param headerBackgroundColor The color of the header background.
 * @param bodyBackgroundColor The color of the body background.
 * @param useBodySurface Whether to use a surface for the body content.
 * @param footer The content to be placed in the footer. If null, the body will take up the free space.
 * @param body The content of the body.
 */
@Composable
fun Background(
    config: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    header: @Composable () -> Unit,
    backgroundColor: Color = PaynesGrey,
    headerBackgroundColor: Color = EggShell,
    bodyBackgroundColor: Color = SilverLakeBlue,
    useBodySurface: Boolean = true,
    footer: @Composable (() -> Unit)? = null,
    body: @Composable () -> Unit
) {
    GomokuTheme {
        val headerMinHeight = config.screenHeight / 12
        val headerMaxHeight = config.screenHeight / 3 + config.screenHeight / 10
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Column {
                // Header
                Column(
                    modifier = Modifier
                        .heightIn(min = headerMinHeight, max = headerMaxHeight)
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = surfaceCornerShapeSize,
                                bottomStart = surfaceCornerShapeSize
                            )
                        )
                        .background(headerBackgroundColor),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.padding(
                            start = headerPaddingHorizontal,
                            end = headerPaddingHorizontal,
                            top = headerPaddingTop,
                            bottom = headerPaddingBottom
                        )
                    ) {
                        header()
                    }
                }
                // Body that includes the optional surface and footer
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = bodySurfacePaddingHorizontal,
                            vertical = bodySurfacePaddingVertical
                        )
                        .let { if (useBodySurface) it.offset(y = bodyOffsetIntoHeader) else it }
                ) {
                    Column {
                        if (useBodySurface) {
                            Column(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(surfaceCornerShapeSize))
                                    .background(bodyBackgroundColor)
                                    .padding(
                                        horizontal = bodyPaddingHorizontal,
                                        vertical = bodyPaddingVertical
                                    )
                            ) {
                                body()
                            }
                        } else {
                            body()
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    footer?.let {
                        Column(
                            modifier = Modifier
                                .padding(
                                    horizontal = footerPaddingHorizontal,
                                    vertical = footerPaddingVertical
                                )
                        ) {
                            it.invoke()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BackgroundWithBodySurfacePreview() {
    Background(
        header = {
            repeat(100) {
                Text(
                    text = "Header: Some very long text repeated way to many times",
                    color = Color.Red
                )
            }
        },
        footer = {
            repeat(100) {
                Text(
                    text = "Footer: Some notes in the footer that no-one reads",
                    color = Color.Red
                )
            }
        },
        useBodySurface = true
    ) {
        repeat(100) {
            Text(
                text = "Body: an actual important text here that is interesting to read",
                color = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BackgroundWithoutBodySurfacePreview() {
    Background(
        header = {
            repeat(100) {
                Text(
                    text = "Header: Some very long text repeated way to many times",
                    color = Color.Red
                )
            }
        },
        footer = {
            repeat(100) {
                Text(
                    text = "Footer: Some notes in the footer that no-one reads",
                    color = Color.Red
                )
            }
        },
        useBodySurface = false
    ) {
        repeat(100) {
            Text(
                text = "Body: an actual important text here that is interesting to read",
                color = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BackgroundWithNavHeaderPreview() {
    Background(
        header = {
            repeat(1) {
                Text(
                    text = "Header: Some very long text repeated way to many times",
                    color = Color.Red
                )
            }
        },
        footer = {
            repeat(100) {
                Text(
                    text = "Footer: Some notes in the footer that no-one reads",
                    color = Color.Red
                )
            }
        }
    ) {
        repeat(100) {
            Text(
                text = "Body: an actual important text here that is interesting to read",
                color = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BackgroundWithNoFooterPreview() {
    Background(
        header = {
            repeat(3) {
                Text(
                    text = "Header: Some very long text repeated way to many times",
                    color = Color.Red
                )
            }
        }
    ) {
        repeat(100) {
            Text(
                text = "Body: an actual important text here that is interesting to read",
                color = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BackgroundWithFooterAndSmallBodyPreview() {
    Background(
        header = {
            repeat(3) {
                Text(
                    text = "Header: Some very long text repeated way to many times",
                    color = Color.Red
                )
            }
        },
        footer = {
            repeat(100) {
                Text(
                    text = "Footer: Some notes in the footer that no-one reads",
                    color = Color.Red
                )
            }
        }
    ) {
        repeat(2) {
            Text(
                text = "Body: an actual important text here that is interesting to read",
                color = Color.Red
            )
        }
    }
}