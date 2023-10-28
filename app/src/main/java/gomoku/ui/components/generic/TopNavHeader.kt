package gomoku.ui.components.generic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gomoku.ui.theme.GomokuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavHeader(
    title: String,
    onBurgerMenuClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 42.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBurgerMenuClick() }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
    )
}

@Composable
@Preview(showBackground = true)
fun TopNavHeaderPreview() {
    GomokuTheme {
        TopNavHeader(title = "Gomoku Royale", onBurgerMenuClick = {})
    }
}
