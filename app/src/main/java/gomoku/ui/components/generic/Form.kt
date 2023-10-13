package gomoku.ui.components.generic

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gomoku.ui.containers.InputFieldData
import gomoku.ui.background.BackgroundConfig
import pdm.gomoku.R

@Composable
fun Form(
    title: String,
    inputFieldsData: List<InputFieldData>,
    submitButtonText: String,
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    formPaddingHorizontal: Dp = 20.dp,
    paddingBetweenInputFields: Dp = 10.dp,
    submitButtonPaddingTop: Dp = 30.dp,
    submitButtonPaddingBottom: Dp = 20.dp,
    submitAction: () -> Unit = {}
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
            InputTextEditor(
                text = inputFieldData.text,
                iconId = inputFieldData.iconId,
                backgroundConfig = backgroundConfig,
            )
        }
        Spacer(
            modifier = Modifier.padding(
                top = submitButtonPaddingTop,
                bottom = submitButtonPaddingBottom
            )
        )
        SubmitButton(text = submitButtonText, backgroundConfig = backgroundConfig) {
            submitAction()
        }
    }
}

@Preview
@Composable
fun RegisterFormPreview() {
    val inputFieldsData = listOf(
        InputFieldData("Find Match", R.drawable.play_button),
        InputFieldData("LeaderBoards", R.drawable.leaderboard)
    )
    Form(
        title = "Introduce your data",
        inputFieldsData = inputFieldsData,
        backgroundConfig = BackgroundConfig(LocalConfiguration.current),
        submitButtonText = "Register"
    )
}