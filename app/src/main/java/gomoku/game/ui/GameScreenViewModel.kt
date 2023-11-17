package gomoku.game.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.LoadState
import gomoku.game.GameService
import gomoku.game.domain.Game
import gomoku.idle
import gomoku.loaded
import gomoku.loading
import kotlinx.coroutines.launch

class GameScreenViewModel(private val service: GameService) : ViewModel() {
    companion object {
        fun factory(service: GameService) = viewModelFactory {
            initializer { GameScreenViewModel(service) }
        }
    }

    var game by mutableStateOf<LoadState<Game>>(idle())
        private set

    fun fetchGame() {
        viewModelScope.launch {
            Log.v(TAG, "entoru no fetchgame")
            game = loading()
            val result = kotlin.runCatching { service.fetchGame() }
            Log.v(TAG, "saiu da corrotina no fetchgame")
            game = loaded(result)
        }
    }
}