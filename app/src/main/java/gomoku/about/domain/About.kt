package gomoku.about.domain

import pdm.gomoku.R

/**
 * Represents section data for the About screen.
 */
object About {
    val sections: List<Section> = listOf(
        Section(
            title = Title("Game Information"),
            description = Description(
                "Gomoku, also known as Five in a Row, is a classic board game that's easy to learn but challenging to master. The goal is simple: be the first player to get five of your stones in a row horizontally, vertically, or diagonally on the game board."
            ),
            // TODO("can domain know about iconIds?")
            iconId = R.drawable.swords
        ),
        Section(
            title = Title("Feedback and Support"),
            description = Description(
                "We value your input! If you have questions, suggestions, or run into any issues while using our app, please don't hesitate to reach out to our support team."
            ),
            iconId = R.drawable.feedback
        ),
        Section(
            title = Title("Authors"),
            description = Description(
                listOf(
                    // TODO("add github url link")
                    Author(49513, "Diogo Rodrigues"),
                    Author(48666, "Tiago Frazao"),
                    Author(49428, "Francisco Engenheiro")
                ).joinToString("\n")
            ),
            iconId = R.drawable.authors
        )
    )
}