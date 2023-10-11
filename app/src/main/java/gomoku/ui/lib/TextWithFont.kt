package gomoku.ui.lib

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import pdm.gomoku.R

@Composable
fun TextWithFont(text: String, maxLines: Int = 1, maxCharsPerLine: Int = Int.MAX_VALUE, textSize: TextUnit = TextUnit.Unspecified, modifier: Modifier = Modifier) {
    val adjustedText = formatText(text, maxCharsPerLine)
    Text(
        adjustedText,
        maxLines = maxLines,
        fontFamily = FontFamily(
            Font(
                R.font.varelaround_regular,
                FontWeight.ExtraBold
            )
        )
    )
}

fun formatText(input: String, maxCharsPerLine: Int): String {
    val words = input.split(' ')
    val result = StringBuilder()
    var lineLength = 0

    for (word in words) {
        if (lineLength + word.length > maxCharsPerLine) {
            result.append('\n')
            lineLength = 0
        }
        result.append(word).append(' ')
        lineLength += word.length + 1 // +1 for the space
    }

    return result.toString().trim() // Remove the trailing space
}