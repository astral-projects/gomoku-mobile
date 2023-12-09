package gomoku.ui.about

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.shared.BaseViewModel

class AboutViewModel(
    preferences: PreferencesRepository
) : BaseViewModel(preferences) {

    companion object {
        fun factory(
            preferences: PreferencesRepository
        ) = viewModelFactory {
            initializer { AboutViewModel(preferences) }
        }
    }
}