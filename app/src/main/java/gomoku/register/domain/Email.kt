package gomoku.register.domain

import gomoku.Validator

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

        override fun validationRuleMsg(): String =
            "Example: john.doe@domain.com"
    }
}