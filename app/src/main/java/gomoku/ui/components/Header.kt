package gomoku.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.wear.compose.material3.Text

@Composable
fun Header(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.inversePrimary,
        fontWeight = FontWeight.Bold
    )
}
