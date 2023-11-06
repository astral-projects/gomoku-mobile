package gomoku.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.ContentAlpha
import pdm.gomoku.R

// Config
private val iconTitleSpacerWidth = 10.dp
private val cardPadding = 10.dp
private val rounderCornerShapeSize = 15.dp
private val descriptionPadding = 10.dp

/**
 * Composes a card that is clickable. When clicked this card reveals the text underneath.
 * To produce that effect this composable uses an animation.
 * Refer to [animateFloatAsState] for more details.
 * @param modifier Modifier to be applied to the card.
 * @param backgroundColor Color of the card background.
 * @param leadingIconId Id of the icon to be placed at the start of the [title].
 * @param title Card title.
 * @param description Card hidden text.
 * @param descriptionMaxLines Max lines the [description] can have. Text above this limit
 * will be treated as text overflow.
 * @param titleColor Color of the [title].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    leadingIconId: Int? = null,
    title: String,
    description: String,
    descriptionMaxLines: Int = Int.MAX_VALUE,
    titleColor: Color = MaterialTheme.colorScheme.secondary,
    iconColor: Color = MaterialTheme.colorScheme.secondary,
) {
    var expandedState by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f // Rotate 180º degrees
        else 0f,
        label = "Rotation State"
    )
    val cornerShape = RoundedCornerShape(rounderCornerShapeSize)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = cornerShape,
        onClick = { expandedState = !expandedState }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(cornerShape)
                .background(backgroundColor)
                .padding(cardPadding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                leadingIconId?.let {
                    Image(
                        painterResource(id = it),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(iconTitleSpacerWidth))
                }
                Text(
                    modifier = Modifier
                        .weight(if (leadingIconId != null) 4f else 6f),
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = titleColor
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = { expandedState = !expandedState }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow",
                        tint = iconColor
                    )
                }
            }
            if (expandedState) {
                Text(
                    modifier = Modifier.padding(descriptionPadding),
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.inversePrimary,
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
private fun ExpandableCardWithIconPreview() {
    ExpandableCard(
        title = "Some random text",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when " +
                "an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        leadingIconId = R.drawable.rule,
        titleColor = Color.Red,
    )
}

@Preview
@Composable
private fun ExpandableCardWithoutIconPreview() {
    ExpandableCard(
        title = "Some random text",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when " +
                "an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        titleColor = Color.Red,
    )
}

@Preview
@Composable
private fun ExpandableCardWithBackgroundPreview() {
    ExpandableCard(
        title = "Some random text",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when " +
                "an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        backgroundColor = Color.Cyan,
        titleColor = Color.Red,
    )
}