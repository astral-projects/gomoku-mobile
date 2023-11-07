package gomoku.shared.components.navigation

/**
 * A navigation item that can be displayed in a navigation drawer.
 * @param title The title of the item.
 * @param selectedIconId The id of the icon to display when the item is selected.
 * @param badgeCount The optional badge count to display on the item.
 * @param onClick The action to perform when the item is clicked.
 * @see NavigationItemGroup
 */
data class NavigationItem(
    val title: String,
    val selectedIconId: Int,
    val badgeCount: Int? = null,
    val onClick: () -> Unit
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NavigationItem) return false
        if (title != other.title) return false
        if (selectedIconId != other.selectedIconId) return false
        if (badgeCount != other.badgeCount) return false
        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + selectedIconId
        result = 31 * result + (badgeCount ?: 0)
        return result
    }
}