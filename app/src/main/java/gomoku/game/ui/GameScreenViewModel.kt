package gomoku.game.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gomoku.GameService
import gomoku.game.domain.Game
import kotlinx.coroutines.launch

class GameScreenViewModel : ViewModel() {

    var game: Game? by mutableStateOf(null)
        private set

    fun fetchGame(service: GameService) {
        viewModelScope.launch {
            game = service.fetchGame()
        }
    }
}