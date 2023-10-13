package gomoku.ui.lib

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gomoku.ui.containers.InputButtonWithImage
import pdm.gomoku.R
@Composable
fun Form_Home_Menu(
    title: String,
    inputFieldsData: List<InputButtonWithImage>, // generealizar com o form
    formPaddingHorizontal: Dp = 20.dp,
    paddingBetweenInputFields: Dp = 10.dp,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = formPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        inputFieldsData.forEach{
            Spacer(modifier = Modifier.padding(paddingBetweenInputFields))
            SubmitButtonWithImage(text = it.text, iconId = it.image) {
            }
        }
    }
}

@Preview
@Composable
fun HomeFormPreview() {
    Form_Home_Menu(title = "Welcome Back User....", listOf(InputButtonWithImage("Close Button", R.drawable.close)))

}