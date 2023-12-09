package gomoku.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gomoku.domain.login.UserInfo
import gomoku.domain.storage.PreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Base class for all view models in the app.
 * @param preferences the repository that provides access to the user's preferences.
 */
abstract class BaseViewModel(
    protected val preferences: PreferencesRepository
) : ViewModel() {

    val isDarkTheme: Flow<Boolean?>
        get() = _isDarkThemeFlow.asStateFlow()

    private val _isDarkThemeFlow: MutableStateFlow<Boolean?> = MutableStateFlow(null)

    fun isDarkTheme() {
        viewModelScope.launch {
            _isDarkThemeFlow.value = preferences.isInDarkMode()
        }
    }

    fun setDarkTheme(isInDarkTheme: Boolean) {
        viewModelScope.launch {
            preferences.setDarkMode(isInDarkTheme)
            _isDarkThemeFlow.value = isInDarkTheme
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getUserInfo(): UserInfo = viewModelScope.async {
        preferences.getUserInfo()
    }.getCompleted() ?: throw IllegalStateException("User info is null.")
}
