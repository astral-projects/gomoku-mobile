package gomoku.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.containers.ButtonData
import pdm.gomoku.R

@Composable
fun Form(
    title: String,
    inputFieldsData: List<ButtonData>,
    formPaddingHorizontal: Dp = 20.dp,
    paddingBetweenInputFields: Dp = 10.dp,
    submitButtonPaddingTop: Dp = 30.dp,
    submitButtonPaddingBottom: Dp = 20.dp,
    footer: @Composable (() -> Unit)? = null,
    renderInputField: @Composable (ButtonData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = formPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Header(text = title)
        inputFieldsData.forEach { inputFieldData ->
            Spacer(modifier = Modifier.padding(paddingBetweenInputFields))
            renderInputField(inputFieldData)

        }
        footer.let {
            Spacer(
                modifier = Modifier
                    .padding(
                        top = submitButtonPaddingTop,
                        bottom = submitButtonPaddingBottom
                    )
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
        ButtonData("Find Match", R.drawable.play_button),
        ButtonData("LeaderBoards", R.drawable.leaderboard)
    )
    Form(
        title = "Introduce your data",
        inputFieldsData = inputFieldsData,
        footer = {
            val boxWidth = backgroundConfig.screenWidth * 0.6f
            val boxHeight = backgroundConfig.screenHeight * 0.04f
            SubmitButton(
                onButtonText = "Register",
                onClick = { },
                modifier = Modifier
                    .height(boxHeight)
                    .width(boxWidth),
                textColor = Color.Black
            )
        },
    ) { inputFieldData ->
        InputTextEditor(
            text = inputFieldData.label,
            iconId = inputFieldData.iconId,
            backgroundConfig = BackgroundConfig(LocalConfiguration.current),
        )
    }
}
