package gomoku.ui.lib

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.screens.backgrounds.BackGround
import gomoku.ui.theme.orangeButtonBorder
import gomoku.ui.theme.orangeButtonInterior

/**
 * Composable of the Input Button
 */
@Composable
fun SubmitButton(
    text: String,
    onClick: () -> Unit
) {
    val background = BackGround(LocalConfiguration.current)
    val boxWidth = background.screenWidth * 0.6f
    val boxHeight = background.screenHeight * 0.04f

    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(7.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = orangeButtonInterior,
        ),
        border = BorderStroke(
            width = 2.dp,
            color = orangeButtonBorder
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
    SubmitButton("Login") {/*TODO*/}
}