package gomoku.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material3.Text

@Composable
fun Header(text: String) {
    Text(
        text = text,
        maxLines = 2,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.inversePrimary,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center
    )
}
