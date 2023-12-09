package gomoku.ui.shared.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

/**
 * A [Text] that represents a title.
 * @param text The text to be displayed in the header.
 */
@Composable
fun HeaderText(text: String) = Text(
    text = text,
    maxLines = 2,
    style = MaterialTheme.typography.titleLarge,
    color = MaterialTheme.colorScheme.inversePrimary,
    fontWeight = FontWeight.Bold,
    overflow = TextOverflow.Ellipsis,
    textAlign = TextAlign.Center
)
