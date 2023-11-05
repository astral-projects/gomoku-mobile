package gomoku.login.domain

import gomoku.Validator

// Constants
private const val MIN_USERNAME_LENGTH = 5
private const val MAX_USERNAME_LENGTH = 30

/**
 * Represents a username.
 * @param value The username value.
 */
@JvmInline
value class Username(val value: String) {

    init {
        require(isValid(value)) { "Invalid username" }
    }

    companion object : Validator<String> {
        override fun isValid(value: String): Boolean {
            if (value.isEmpty()) return false
            if (value.isBlank()) return false
            if (value.length !in MIN_USERNAME_LENGTH..MAX_USERNAME_LENGTH) return false
            return true
        }

        override fun validationRuleMsg() =
            "Must be between $MIN_USERNAME_LENGTH-$MAX_USERNAME_LENGTH characters long."
    }
}