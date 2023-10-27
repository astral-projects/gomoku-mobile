package gomoku.ui.components.generic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.unit.sp
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.theme.EggShell
import pdm.gomoku.R


private val BorderWidth = 2.dp
private val RoundedCornerShapeSize = 7.dp
//TODO(Be careful with the size of the text, it can be too big for the button, this numbers with was used to the homeScreen, BUT
// if you need another size, we need to somehow pass the size of the button to the textWithFont, so it can be adjusted to the size of the button)
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
    val boxHeight = backgroundConfig.screenHeight * 0.07f

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
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(40.dp).align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(8.dp))


            TextWithFont(text = text,color= Color.Black, textSize = 20.sp , modifier = Modifier.width(boxWidth).height(boxHeight).offset(y = 6.dp))
        }
    }
}
@Composable
@Preview(showBackground = true)
fun SubmitButtonWithImagePreview() {
    SubmitButtonWithImage("Find Match", R.drawable.play_button, interiorColor = EggShell, onClick = {})
}