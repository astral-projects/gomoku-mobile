package gomoku.domain.about

/**
 * Represents an author.
 * @param name the name of the author.
 * @param githubUrl the GitHub url of the author.
 */
data class Author(
    val name: String,
    val githubUrl: String
)