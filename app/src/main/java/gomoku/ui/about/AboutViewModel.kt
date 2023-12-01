package gomoku.ui.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.storage.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AboutViewModel(
    private val preferences: PreferencesRepository
) : ViewModel() {

    companion object {
        fun factory(
            preferences: PreferencesRepository
        ) = viewModelFactory {
            initializer { AboutViewModel(preferences) }
        }
    }


    val isDarkTheme: Flow<Boolean?>
        get() = _isDarkThemeFlow.asStateFlow()

    private val _isDarkThemeFlow: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)

    fun isDarkTheme() {
        viewModelScope.launch {
            _isDarkThemeFlow.value = preferences.isInDarkMode()
        }
    }

    fun setDarkTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            preferences.setDarkMode(isDarkTheme)
            _isDarkThemeFlow.value = isDarkTheme
        }
    }

}