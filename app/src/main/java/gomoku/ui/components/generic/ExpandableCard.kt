package gomoku.ui.components.generic

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.ContentAlpha.medium

/**
 * Composes a card that is clickable. When clicked this card reveals the text underneath.
 * To produce that effect this composable uses an animation.
 * Refer to [animateFloatAsState] for more details.
 * @param title Card title.
 * @param description Card hidden text.
 * @param descriptionMaxLines Max lines the [description] can have. Text above this limit
 * will be treated as text overflow.
 * @param painter Painter to draw. Usually an icon.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    title: String,
    description: String,
    descriptionMaxLines: Int = 10,
    painter: Painter? = null,
    colorTittle: Color = MaterialTheme.colorScheme.secondary,
    ) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f // Rotate 180º degrees
        else 0f, label = ""
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        colors= CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(4.dp),
        onClick = { expandedState = !expandedState }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                painter?.let {
                    Icon(
                        modifier = Modifier
                            .weight(1f),
                        painter = it,
                        contentDescription = "Icon"
                    )
                }
                Text(
                    modifier = Modifier
                        .weight(if (painter != null) 4f else 6f),
                    text = title,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colorTittle
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(medium)
                        .rotate(rotationState),
                    onClick = { expandedState = !expandedState }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow",
                        tint = colorTittle
                    )
                }
            }
            if (expandedState) {
                Text(
                    text = description,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Normal,
                    maxLines = descriptionMaxLines,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview
@Composable
private fun TestExpandableCard() {
    ExpandableCard(
        title = "Some random text",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when " +
                "an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        painter = null,
        colorTittle = Color.Yellow,
    )
}