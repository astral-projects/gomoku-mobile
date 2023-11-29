package gomoku.ui.shared.components.navigationDrawer

/**
 * Represents a group of [NavigationItem]s that can be displayed in a navigation drawer.
 * @param title The optional title of the item group.
 * @param items The list of [NavigationItem]s to display.
 */
data class NavigationItemGroup(
    val title: String?,
    val items: List<NavigationItem>
)