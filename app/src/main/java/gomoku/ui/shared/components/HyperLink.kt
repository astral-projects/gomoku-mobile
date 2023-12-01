package gomoku.ui.shared.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview

/**
 * A [ClickableText] that can be used to navigate to a link.
 * @param text The text to be displayed.
 * @param onClick The callback to be executed when the text is clicked.
 */
@Composable
fun HyperLink(text: String, onClick: (Int) -> Unit) =
    ClickableText(
        modifier= Modifier.testTag(text),
        text = AnnotatedString(text),
        onClick = onClick,
        style = MaterialTheme
            .typography
            .bodyMedium
            .copy(
                color = MaterialTheme.colorScheme.onPrimary,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )
    )

@Composable
@Preview
private fun HyperLinkPreview() {
    HyperLink(onClick = {}, text = "Sign up")
}