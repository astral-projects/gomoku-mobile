package gomoku.domain.service.game.errors

class FetchGameException(
    message: String = "Could not fetch game",
    cause: Throwable? = null
) : Exception(message, cause)