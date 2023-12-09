package gomoku.domain.login

import gomoku.domain.Validator
import pdm.gomoku.R

// Constants
private const val MIN_PASSWORD_LENGTH = 8
private const val MAX_PASSWORD_LENGTH = 40

/**
 * Represents a password.
 * @param value The password value.
 */
@JvmInline
value class Password(val value: String) {

    init {
        require(isValid(value)) { "Invalid password" }
    }

    companion object : Validator<String> {
        override fun isValid(value: String): Boolean {
            if (value.isEmpty()) return false
            if (value.isBlank()) return false
            if (value.length !in MIN_PASSWORD_LENGTH - 1..<MAX_PASSWORD_LENGTH) return false
            return true
        }

        override val validationRuleResourceId = R.string.password_validation_rule_msg

        const val minPasswordLength = MIN_PASSWORD_LENGTH
        const val maxPasswordLength = MAX_PASSWORD_LENGTH
    }
}