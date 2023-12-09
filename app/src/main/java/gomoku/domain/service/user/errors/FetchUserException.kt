package gomoku.domain.service.user.errors

class FetchUserException(
    message: String = "Could not fetch user.",
    cause: Throwable? = null
) : Exception(message, cause)