package gomoku.ui.popups

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

// Config
const val POPUP_WIDTH_FACTOR = 0.8f
val popupRoundCornerShapeSize = 20.dp

/**
 * A popup that can be used to display a popup in app's domain.
 * The popup is dismissible by clicking outside of it or by pressing the back button.
 * @param onDismissRequest callback to be invoked when the user dismisses the popup.
 * @param content the content of the popup.
 */
@Composable
fun DomainPopup(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Popup(
        properties = PopupProperties(
            focusable = true,
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        ),
        onDismissRequest = onDismissRequest
    ) { content() }
}