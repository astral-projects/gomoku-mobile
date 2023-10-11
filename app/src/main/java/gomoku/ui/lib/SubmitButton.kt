package gomoku.ui.lib

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.screens.background.BackgroundConfig
import gomoku.ui.theme.orangeButtonBorder
import gomoku.ui.theme.orangeButtonInterior

private val BorderWidth = 2.dp
private val RoundedCornerShapeSize = 7.dp

@Composable
fun SubmitButton(
    text: String,
    interiorColor: Color = orangeButtonInterior,
    borderColor: Color = orangeButtonBorder,
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onClick: () -> Unit
) {
    val boxWidth = backgroundConfig.screenWidth * 0.6f
    val boxHeight = backgroundConfig.screenHeight * 0.04f

    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(RoundedCornerShapeSize),
        colors = ButtonDefaults.buttonColors(
            containerColor = interiorColor,
        ),
        border = BorderStroke(
            width = BorderWidth,
            color = borderColor
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