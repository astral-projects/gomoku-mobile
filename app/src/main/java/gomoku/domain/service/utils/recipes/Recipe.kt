package gomoku.domain.service.utils.recipes

/**
 * Represents a recipe from the response of the initial API call.
 * @property rel The list of relations for the recipe.
 * @property href The link to the recipe.
 */
data class Recipe(
    val rel: String,
    val href: String
)