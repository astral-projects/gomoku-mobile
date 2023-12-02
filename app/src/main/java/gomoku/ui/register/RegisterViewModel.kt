package gomoku.ui.register

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.Fail
import gomoku.domain.IOState
import gomoku.domain.Idle
import gomoku.domain.Loaded
import gomoku.domain.fail
import gomoku.domain.idle
import gomoku.domain.loaded
import gomoku.domain.loading
import gomoku.domain.service.user.UserService
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.shared.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val service: UserService,
    preferences: PreferencesRepository
) : BaseViewModel(preferences) {

    companion object {
        fun factory(
            service: UserService,
            preferences: PreferencesRepository
        ) = viewModelFactory {
            initializer { RegisterViewModel(service, preferences) }
        }
    }

    val userId: Flow<IOState<Int>>
        get() = _createUserIdFlowInfo.asStateFlow()

    private val _createUserIdFlowInfo: MutableStateFlow<IOState<Int>> =
        MutableStateFlow(idle())

    fun register(
        username: String,
        email: String,
        password: String
    ) {
        if (_createUserIdFlowInfo.value !is Idle && _createUserIdFlowInfo.value !is Fail)
            throw IllegalStateException("The view model is not in the idle state.")
        _createUserIdFlowInfo.value = loading()
        viewModelScope.launch {
            val result =
                runCatching { service.register(username, email, password) }
            if (result.isFailure) {
                _createUserIdFlowInfo.value = fail()
            } else {
                _createUserIdFlowInfo.value = loaded(result)
            }
        }
    }

    /**
     * Resets the view model to the idle state. From the idle state, the create user
     * can be fetched again.
     */
    fun resetToIdle() {
        if (_createUserIdFlowInfo.value !is Loaded)
            throw IllegalStateException("The view model is not in the idle state.")
        _createUserIdFlowInfo.value = idle()
    }
}
