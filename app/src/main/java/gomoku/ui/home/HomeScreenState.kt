package gomoku.ui.home

sealed class HomeScreenState {
    data object Idle : HomeScreenState()
    data class Error(val message: Throwable) : HomeScreenState()
    data class Logout(val isDone: Boolean = false) : HomeScreenState()
}