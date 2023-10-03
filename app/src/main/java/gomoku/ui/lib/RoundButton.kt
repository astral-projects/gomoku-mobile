package gomoku.ui.lib

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A round button.
 *
 * @param modifier The modifier to be applied to the button.
 * @param onClick The callback to be invoked when the button is clicked.
 * @param enabled Whether the button is enabled.
 * @param content The content of the button.
 */
@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable () -> Unit = {},
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.size(75.dp),
        contentPadding = PaddingValues(0.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp),
        enabled = enabled
    ) {
        content()
    }
}


@Preview(showBackground = true)
@Composable
fun RoundButtonPreview() {
    RoundButton(onClick = {}) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Increment",
        )
    }
}
