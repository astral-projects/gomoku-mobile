package gomoku.login.domain

import gomoku.register.domain.Email

/**
 * Validates the registration and login credentials.
 */
object AuthValidator {

    /**
     * Checks if the registration credentials are valid, according to the validation rules.
     * @param username The username to be validated.
     * @param email The email to be validated.
     * @param password The password to be validated.
     * @param confirmPassword The confirm password to be validated. Must be equal to the password.
     * @return true if the fields are valid, false otherwise.
     */
    fun areRegistrationCredentialsValid(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean =
        Username.isValid(username) &&
                Email.isValid(email) &&
                Password.isValid(password) &&
                Password.isValid(confirmPassword) && password == confirmPassword

    /**
     * Checks if the login credentials are valid, according to the validation rules.
     * @param username The username to be validated.
     * @param password The password to be validated.
     * @return true if the fields are valid, false otherwise.
     */
    fun areLoginCredentialsValid(username: String, password: String): Boolean =
        Username.isValid(username) && Password.isValid(password)

}