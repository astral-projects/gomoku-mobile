package gomoku.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gomoku.domain.storage.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
}
