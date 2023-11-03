package gomoku.ui.components.generic

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import pdm.gomoku.R


@Composable
@Deprecated("Use Text instead")
fun TextWithFont(text: String, maxLines: Int = 1, maxCharsPerLine: Int = Int.MAX_VALUE, textSize: TextUnit = TextUnit.Unspecified, modifier: Modifier = Modifier,color:Color = Color.Black) {
    val adjustedText = formatText(text, maxCharsPerLine)
    Text(modifier = modifier,
        text = adjustedText,
        maxLines = maxLines,
        fontFamily = FontFamily(
            Font(
                R.font.varelaround_regular,
                FontWeight.ExtraBold
            )
        ), color = color, fontSize = textSize
    )
   // TODO("Tirar este TextWithFont e tentar usar o Text para usar o formatText")
}

@Composable
@Preview
fun totest(){
    TextWithFont("Teste")
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