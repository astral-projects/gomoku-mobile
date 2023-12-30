package gomoku.http.models.games

data class ExitGameOutputModel(
    val gameId: Int,
    val message: String,
)