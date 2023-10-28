package gomoku.ui.components.generic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.theme.GomokuTheme
import pdm.gomoku.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    text: String,
    iconId: Int,
    modifier: Modifier = Modifier,
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
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary
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
    GomokuTheme {
        SearchBar(
            text = "Search a player...",
            iconId = R.drawable.search,
            onSearch = {}
        )
    }
}