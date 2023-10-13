package gomoku.ui.lib

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.screens.background.BackgroundConfig
import gomoku.ui.theme.orangeButtonInterior
import pdm.gomoku.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    text: String,
    iconId: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = orangeButtonInterior,
    backgroundConfig: BackgroundConfig = BackgroundConfig(LocalConfiguration.current),
    onSearch: (String) -> Unit,
) {

    // state that remembers the current text
    var currentText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = currentText,
        onValueChange = {
            currentText = it
            onSearch(it)
        },
        label = {
            Text(
                text = text
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = backgroundColor,
        ),
        // Add image to the right of the text field
        trailingIcon = {
            Image(
                painter = painterResource(iconId),
                contentDescription = null
            )
        },
    )
}

@Composable
@Preview(showBackground = true)
fun SearchBarPreview() {
    SearchBar(
        text = "Search a player...",
        iconId = R.drawable.search,
        onSearch = {}
    )
}