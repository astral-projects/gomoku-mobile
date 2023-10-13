package gomoku.ui.lib

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.components.generic.TextWithFont
import gomoku.ui.theme.EggShell
import pdm.gomoku.R


private val BorderWidth = 2.dp
private val RoundedCornerShapeSize = 7.dp
@Composable
fun SubmitButtonWithImage(
    text: String,
    iconId: Int,
    interiorColor: Color = EggShell,
    borderColor: Color = Color.Black,
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onClick: () -> Unit
) {
    val boxWidth = backgroundConfig.screenWidth * 0.9f
    val boxHeight = backgroundConfig.screenHeight * 0.05f

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
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone ou Imagem
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(20.dp).align(Alignment.CenterVertically) // Ajuste o tamanho conforme necessário
            )

            Spacer(modifier = Modifier.width(8.dp)) // Espaçador entre ícone e texto

            // Texto
            TextWithFont(text = text,color= Color.Black)
        }
    }
}
@Composable
@Preview(showBackground = true)
fun SubmitButtonWithImagePreview() {
    SubmitButtonWithImage("Find Match", R.drawable.play_button, interiorColor = EggShell, onClick = {})
}