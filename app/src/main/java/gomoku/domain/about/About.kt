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
            description = Description(R.string.about_section_c_description),
            iconId = R.drawable.authors
        )
    )
}