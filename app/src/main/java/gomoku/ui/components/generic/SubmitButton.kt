package gomoku.ui.components.generic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.background.BackgroundConfig

private val borderWidth = 2.dp
private val roundedCornerShapeSize = 7.dp

@Composable
fun SubmitButton(
    text: String,
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onClick: () -> Unit
) {
    val boxWidth = backgroundConfig.screenWidth * 0.6f
    val boxHeight = backgroundConfig.screenHeight * 0.04f

    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(roundedCornerShapeSize),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        border = BorderStroke(
            width = borderWidth,
            color = MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier
            .height(boxHeight)
            .width(boxWidth)
    ) {
        TextWithFont(text = text)
    }
}

@Composable
@Preview(showBackground = true)
fun OrangeButtonPreview() {
    SubmitButton("Login", onClick = {})
}