package gomoku.domain.service.user

import gomoku.domain.leaderboard.Term
import gomoku.domain.leaderboard.UserStats
import gomoku.domain.login.UserInfo
import gomoku.domain.service.user.errors.FetchUserException
import gomoku.domain.service.user.errors.RegisterUserException

/**
 * Defines the behavior of a user service.
 */
interface UserService {

    /**
     * Attempts to log in a user with the given username and password.
     * @param username The username of the user to log in.
     * @param password The password of the user to log in.
     * @return The user's information if the login attempt is successful.
     * @throws FetchUserException If the user with the given username and password cannot be found.
     */
    @Throws(FetchUserException::class)
    suspend fun login(
        username: String,
        password: String,
    ): UserInfo

    /**
     * Registers a new user with the given username, email, and password.
     * @param username The username of the user to register.
     * @param email The email address of the user to register.
     * @param password The password of the user to register.
     * @return The id of the newly registered user if the registration is successful.
     * @throws RegisterUserException If the user could not be registered.
     */
    @Throws(RegisterUserException::class)
    suspend fun register(
        username: String,
        email: String,
        password: String
    ): Int

    /**
     * Searches for users matching the given term.
     * @param term The term to search for.
     * @return A list of user statistics matching the search term, which could be empty.
     */
    suspend fun searchUsers(term: Term): List<UserStats>

    /**
     * Fetches the user statistics for the given page.
     * @param page The page number to fetch.
     * @param itemsPerPage The number of items per page by default is 10.
     * @return A list of user statistics for the given page, which could be empty.
     */
    suspend fun fetchUsersStats(page: Int, itemsPerPage: Int = 10): List<UserStats>

    /**
     * Fetches the user statistics for the given user id.
     * @param userId The id of the user to fetch the statistics for.
     * @return The user statistics for the given user id.
     * @throws FetchUserException If the user with the given id cannot be found.
     */
    @Throws(FetchUserException::class)
    suspend fun fetchUserStats(userId: Int): UserStats

    /**
     * Attempts to logout the logged in user.
     * @param token The token of the logged in user.
     */
    suspend fun logout(token: String)
}
