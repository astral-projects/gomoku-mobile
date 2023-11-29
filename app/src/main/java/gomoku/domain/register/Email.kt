package gomoku.domain.register

import gomoku.domain.Validator
import pdm.gomoku.R

// Constants
private const val REGEX_PATTERN = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\$"

/**
 * Represents a valid email address.
 */
@JvmInline
value class Email(val value: String) {

    init {
        require(isValid(value)) { "Invalid email address" }
    }

    companion object : Validator<String> {
        override fun isValid(value: String): Boolean =
            value.matches(Regex(REGEX_PATTERN))

        override val validationRuleResourceId = R.string.email_validation_rule_msg
    }
}