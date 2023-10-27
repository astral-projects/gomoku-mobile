package gomoku

import android.app.Activity

/**
 * Interface for navigation between screens.
 */
interface Navigation {
    fun navigateTo(origin: Activity)
}