package gomoku.ui.lib

import android.util.Size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import pdm.gomoku.R

@Composable
fun TextWithFont(text: String, textSize: TextUnit = TextUnit.Unspecified, modifier: Modifier = Modifier) {
    Text(
        text,
        fontFamily = FontFamily(
            Font(
                R.font.varelaround_regular,
                FontWeight.ExtraBold
            )
        )
    )
}