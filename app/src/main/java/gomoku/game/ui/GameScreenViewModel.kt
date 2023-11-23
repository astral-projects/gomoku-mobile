package gomoku.game.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.Idle
import gomoku.LoadState
import gomoku.Loaded
import gomoku.game.GameService
import gomoku.game.domain.Game
import gomoku.game.domain.moves.Move
import gomoku.idle
import gomoku.loaded
import gomoku.loading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GameScreenViewModel(private val service: GameService) : ViewModel() {

    companion object {
        fun factory(service: GameService) = viewModelFactory {
            initializer { GameScreenViewModel(service) }
        }
    }

    val game: Flow<LoadState<Game?>>
        get() = _gameFlow

    private val _gameFlow: MutableStateFlow<LoadState<Game?>> = MutableStateFlow(idle())
    fun fetchGame() {
        if (_gameFlow.value !is Idle)
            throw IllegalStateException("The view model is not in the idle state.")
        Log.v(TAG, "goes to LoadingState")
        _gameFlow.value = loading()
        viewModelScope.launch {
            Log.v(TAG, "fetch game")
            val result = runCatching { service.fetchGame() }
            Log.v(TAG, "exit fetch game")
            _gameFlow.value = loaded(result)
        }
    }

    fun makeMove(move: Move) {
        /*if (_gameFlow.value !is Loaded)
            throw IllegalStateException("The view model is not in the loaded state.Is not possible to make a move")
        viewModelScope.launch {
            val result = runCatching { service.makeMove(move) }
            if(result.isSuccess){
                _gameFlow.value = loaded(result)
            }else{
                _gameFlow.value = _gameFlow.value
                Log.v(TAG, "Error making move")
            }
        }*/
    }

    /**
     * Resets the view model to the idle state. From the idle state, the user information
     * can be fetched again.
     */
    fun resetToIdle() {
        if (_gameFlow.value !is Loaded)
            throw IllegalStateException("The view model is not in the idle state.")
        _gameFlow.value = idle()
    }
}