package gomoku.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gomoku.home.domain.ButtonData
import pdm.gomoku.R

// Config
private val defaultFormPaddingHorizontal = 20.dp
private val defaultPaddingBetweenInputFields = 10.dp
private val defaultFooterPaddingTop = 30.dp
private val defaultFooterPaddingBottom = 20.dp

/**
 * A generic form composable.
 * @param title The title of the form.
 * @param inputFieldsData The data of the input fields.
 * @param formPaddingHorizontal The horizontal padding of the form.
 * @param paddingBetweenInputFields The padding between the input fields.
 * @param footerPaddingTop The footer top padding.
 * @param footerPaddingBottom The footer bottom padding.
 * @param footer The optional footer of the form, like a submit button.
 * @param renderInputField The function that renders the input field for each input field data.
 */
@Composable
fun <R> Form(
    title: String,
    inputFieldsData: List<R>,
    formPaddingHorizontal: Dp = defaultFormPaddingHorizontal,
    paddingBetweenInputFields: Dp = defaultPaddingBetweenInputFields,
    footerPaddingTop: Dp = defaultFooterPaddingTop,
    footerPaddingBottom: Dp = defaultFooterPaddingBottom,
    footer: @Composable (() -> Unit)? = null,
    renderInputField: @Composable (R) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = formPaddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeaderText(text = title)
        inputFieldsData.forEach { inputFieldData ->
            Spacer(modifier = Modifier.padding(paddingBetweenInputFields))
            renderInputField(inputFieldData)
        }
        footer.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = footerPaddingTop, bottom = footerPaddingBottom),
                horizontalArrangement = Arrangement.Center,
            ) {
                it?.invoke()
            }
        }
    }
}

@Preview
@Composable
private fun RegisterFormPreview() {
    val inputFieldsData = listOf(
        ButtonData("Find Match", R.drawable.play_button),
        ButtonData("LeaderBoards", R.drawable.leaderboard)
    )
    Form(
        title = "Introduce your data",
        inputFieldsData = inputFieldsData,
        footer = {
            SubmitButton(
                onButtonText = "Register",
                onClick = { },
                textColor = Color.Black
            )
        },
    ) { inputFieldData ->
        InputTextField(
            value = "value",
            label = inputFieldData.label,
            leadingIconId = inputFieldData.iconId,
            onValueChange = {},
        )
    }
}
