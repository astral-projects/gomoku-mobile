package gomoku.about.domain

class Author (private val number: Int, val name: String) {
    override fun toString() = "$number - $name"
}
