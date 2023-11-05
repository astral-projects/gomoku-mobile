package gomoku.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material3.Text

/**
 * A [Text] that represents a headline.
 * @param text The text to be displayed in the header.
 */
@Composable
fun HeadlineText(text: String) = Text(
    text = text,
    maxLines = 1,
    style = MaterialTheme.typography.headlineMedium,
    color = MaterialTheme.colorScheme.inversePrimary,
    fontWeight = FontWeight.Bold,
    overflow = TextOverflow.Ellipsis,
    textAlign = TextAlign.Center
)
