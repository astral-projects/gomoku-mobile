package gomoku.domain.service.game.errors

class FetchGameException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)