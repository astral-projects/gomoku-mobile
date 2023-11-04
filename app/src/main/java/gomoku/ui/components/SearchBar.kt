package gomoku.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import gomoku.ui.components.generic.OutlinedTextFieldWithValidation
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
    OutlinedTextFieldWithValidation(
        iconId = iconId,
        placeholderText = text,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent
        ),
        modifier = modifier,
        searchField = onSearch,
        validateField = { true }
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