package gomoku.ui.lib

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HyperLink (onClick: (Int) -> Unit, text: String) {
    ClickableText(
        text = AnnotatedString(text),
        onClick = onClick,
        style = MaterialTheme
            .typography
            .bodyMedium
            .copy(color = Color.White),

    )
}

@Composable
@Preview
fun HyperLinkPreview() {
    HyperLink(onClick = {/*TODO*/}, text = "Sign up")
}