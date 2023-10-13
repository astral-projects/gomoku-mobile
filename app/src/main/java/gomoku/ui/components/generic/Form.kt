package gomoku.ui.components.generic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.containers.InputFieldData
import pdm.gomoku.R

@Composable
fun Form(
    title: String,
    inputFieldsData: List<InputFieldData>,
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    formPaddingHorizontal: Dp = 20.dp,
    paddingBetweenInputFields: Dp = 10.dp,
    submitButtonPaddingTop: Dp = 30.dp,
    submitButtonPaddingBottom: Dp = 20.dp,
    submitAction: () -> Unit = {},
    footer: @Composable (() -> Unit)? = null,
    renderInputField: @Composable (InputFieldData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = formPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        inputFieldsData.forEach { inputFieldData ->
            Spacer(modifier = Modifier.padding(paddingBetweenInputFields))
            renderInputField(inputFieldData)

        }
        footer.let {
            Spacer(
                modifier = Modifier.padding(
                    top = submitButtonPaddingTop,
                    bottom = submitButtonPaddingBottom
                ).background(Color.Red)
            )
            it?.invoke()
        }

    }
}

@Preview
@Composable
fun RegisterFormPreview() {
    val backgroundConfig = BackgroundConfig(LocalConfiguration.current)
    val inputFieldsData = listOf(
        InputFieldData("Find Match", R.drawable.play_button),
        InputFieldData("LeaderBoards", R.drawable.leaderboard)
    )
    Form(
        title = "Introduce your data",
        inputFieldsData = inputFieldsData,
        backgroundConfig = backgroundConfig,
        footer = {
            val boxWidth = backgroundConfig.screenWidth * 0.6f
            val boxHeight = backgroundConfig.screenHeight * 0.04f
            SubmitButton(
                onButtonText = "Register",
                onClick = { },
                modifier = Modifier
                    .height(boxHeight)
                    .width(boxWidth),
                letterColor = Color.Black
            )
        },
    ) { inputFieldData ->
        InputTextEditor(
            text = inputFieldData.text,
            iconId = inputFieldData.iconId,
            backgroundConfig = BackgroundConfig(LocalConfiguration.current),
        )
    }
}
