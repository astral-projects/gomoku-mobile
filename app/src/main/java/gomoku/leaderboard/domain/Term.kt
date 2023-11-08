package gomoku.leaderboard.domain

import gomoku.Validator
import pdm.gomoku.R

// Constants
private const val MIN_TERM_LENGTH = 3

/**
 * Represents a search term.
 * @param value The term value.
 */
@JvmInline
value class Term(val value: String) {

    init {
        require(isValid(value)) { "Invalid term value" }
    }

    companion object : Validator<String> {
        fun toTermOrNull(value: String): Term? {
            return if (isValid(value)) Term(value) else null
        }

        override fun isValid(value: String): Boolean {
            if (value.isEmpty()) return false
            if (value.isBlank()) return false
            if (value.length < MIN_TERM_LENGTH) return false
            return true
        }

        override val validationRuleResourceId: Int
            get() = R.string.term_validation_rule_msg

    }
}