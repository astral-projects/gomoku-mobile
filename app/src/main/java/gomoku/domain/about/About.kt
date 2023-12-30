package gomoku.domain.about

import pdm.gomoku.R

/**
 * Represents data and functionality related to the about screen.
 */
object About {
    val sections: List<Section> = listOf(
        Section(
            title = Title(R.string.about_section_a_title),
            description = Description(R.string.about_section_a_description),
            iconId = R.drawable.swords
        ),
        Section(
            title = Title(R.string.about_section_b_title),
            description = Description(R.string.about_section_b_description),
            iconId = R.drawable.feedback
        ),
        Section(
            title = Title(R.string.about_section_c_title),
            iconId = R.drawable.authors
        )
    )
    val authors = listOf(
        Author(
            name = "Diogo Rodrigues",
            githubUrl = "https://github.com/Diogofmr"
        ),
        Author(
            name = "Tiago Frazão",
            githubUrl = "https://github.com/TiagoFrazao01"
        ),
        Author(
            name = "Francisco Engenheiro",
            githubUrl = "https://github.com/FranciscoEngenheiro"
        ),
    )
}