package gomoku.domain

/**
 * A validator that can be used to validate a value of type [R].
 * @param R The type of the value to be validated.
 */
interface Validator<R> {
    /**
     * Validates the given value.
     */
    fun isValid(value: R): Boolean

    /**
     * Returns the validation rule message resource id.
     */
    val validationRuleResourceId: Int
}
