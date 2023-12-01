package gomoku.domain.about

/**
 * Represents an author of the app.
 * @param number the author's number.
 * @param name the author's name.
 */
class Author(private val number: Int, val name: String) {
    override fun toString() = "$number - $name"
}
