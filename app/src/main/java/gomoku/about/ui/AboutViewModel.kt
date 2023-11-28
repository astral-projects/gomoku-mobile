package gomoku.about.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AboutViewModel(
    private val themeRepo: ThemeRepository
) : ViewModel() {

    companion object {
        fun factory(
            themeRepo: ThemeRepository
        ) = viewModelFactory {
            initializer { AboutViewModel(themeRepo) }
        }
    }


    val isDarkTheme: Flow<Boolean?>
        get() = _isDarkThemeFlow.asStateFlow()

    private val _isDarkThemeFlow: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)

    fun isDarkTheme() {
        viewModelScope.launch {
            _isDarkThemeFlow.value = themeRepo.getIsDarkTheme()
        }
    }

    fun setDarkTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            themeRepo.setDarkTheme(isDarkTheme)
            _isDarkThemeFlow.value = isDarkTheme
        }
    }

}