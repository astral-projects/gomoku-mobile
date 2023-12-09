package gomoku.domain.service.game.errors

class FetchLobbyException(
    message: String = "Could not fetch lobby",
    cause: Throwable? = null
) : Exception(message, cause)