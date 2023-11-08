package gomoku.services

/**
 * Represents an error that occurred while fetching a user.
 * @param message A message describing the error.
 * @param cause The cause of the error.
 */
class FetchUserException(message: String, cause: Throwable? = null) : Exception(message, cause)