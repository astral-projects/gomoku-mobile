package gomoku.domain.about

/**
 * Represents a section in the about screen.
 * @param title the title of the section.
 * @param description optional description of the section.
 * @param iconId the icon id of the section.
 */
class Section(val title: Title, val description: Description? = null, val iconId: Int)