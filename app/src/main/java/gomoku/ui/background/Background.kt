package gomoku.ui.background

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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

// Config
private val surfaceCornerShapeSize: Dp = 35.dp
private val headerPaddingHorizontal: Dp = 20.dp
private val headerPaddingTop: Dp = 20.dp
private val headerPaddingBottom: Dp = 40.dp
private val surfacePaddingHorizontal: Dp = 35.dp
private val surfacePaddingVertical: Dp = 10.dp
private val bodyPaddingHorizontal: Dp = 10.dp
private val bodyPaddingVertical: Dp = 15.dp
private val bodySurfacePaddingHorizontal: Dp = 20.dp
private val bodySurfacePaddingVertical: Dp = 10.dp
private val footerPaddingVertical: Dp = 2.dp
private val bodyOffsetIntoHeader: Dp = (-30).dp
private val bodySurfaceBorderWidth: Dp = 2.dp
private val headerBorderWidth: Dp = Dp.Hairline
private val headerCornerShapeSize = RoundedCornerShape(
    bottomEnd = surfaceCornerShapeSize,
    bottomStart = surfaceCornerShapeSize
)

/**
 * A background that has a [header], [body], and an optional [footer].
 * All content is centered and restricted to a maximum width and height.
 * Header can only go down to 2/5 of the screen height.
 * @param config The configuration of the screen.
 * @param header The content of the header.
 * @param useBodySurface Whether to use a surface for the body content.
 * @param footer The content to be placed in the footer. If null, the body will take up the free space.
 * @param body The content of the body.
 */
@Composable
fun Background(
    config: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    header: @Composable () -> Unit,
    useBodySurface: Boolean = true,
    footer: @Composable (() -> Unit)? = null,
    body: @Composable () -> Unit
) {
    val headerMinHeight = config.screenHeight / 12
    val headerMaxHeight = config.screenHeight / 3 + config.screenHeight / 10
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            // Header
            Column(
                modifier = Modifier
                    .heightIn(min = headerMinHeight, max = headerMaxHeight)
                    .fillMaxWidth()
                    .clip(headerCornerShapeSize)
                    .background(MaterialTheme.colorScheme.primary)
                    .border(
                        width = headerBorderWidth,
                        color = MaterialTheme.colorScheme.outline,
                        shape = headerCornerShapeSize
                    ),
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
                        horizontal = if (useBodySurface) surfacePaddingHorizontal else bodyPaddingHorizontal,
                        vertical = if (useBodySurface) surfacePaddingVertical else bodyPaddingVertical
                    )
                    // goes into the header
                    .let { if (useBodySurface) it.offset(y = bodyOffsetIntoHeader) else it }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (useBodySurface) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(surfaceCornerShapeSize))
                                .border(
                                    width = bodySurfaceBorderWidth,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(surfaceCornerShapeSize)
                                )
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(
                                    horizontal = bodySurfacePaddingHorizontal,
                                    vertical = bodySurfacePaddingVertical
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
                            .padding(vertical = footerPaddingVertical)
                    ) {
                        it.invoke()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BackgroundWithBodySurfacePreview() {
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

@Preview
@Composable
private fun BackgroundWithoutBodySurfacePreview() {
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
private fun BackgroundWithNavHeaderPreview() {
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
private fun BackgroundWithNoFooterPreview() {
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