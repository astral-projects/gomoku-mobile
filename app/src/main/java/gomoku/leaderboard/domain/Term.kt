package gomoku.leaderboard.domain

import gomoku.Validator

/**
 * Represents a password.
 * @param value The password value.
 */
@JvmInline
value class Term(val value: String) {

    init {
        require(isValid(value)) { "Invalid password" }
    }

    companion object : Validator<String> {
        fun toTermOrNull(value: String): Term? {
            return if (isValid(value)) Term(value) else null
        }

        override fun isValid(value: String): Boolean {
            if (value.isEmpty()) return false
            if (value.isBlank()) return false
            return true
        }

        override fun validationRuleMsg() =
            "Must not be empty or blank"

    }
}