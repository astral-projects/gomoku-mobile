package gomoku

import android.app.Activity

/**
 * Contract centralizes all the navigation logic in the activities that implement it.
 */
interface Navigation {
    fun navigateTo(origin: Activity)
}