package gomoku.domain.service.user.errors

class RegisterUserException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)
