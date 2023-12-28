package gomoku.http.models.lobby

data class DeleteLobbyOutputModel(
    val lobbyId: Int,
    val message: String,
)