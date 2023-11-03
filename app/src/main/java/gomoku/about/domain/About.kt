package gomoku.about.domain

class About {
    companion object {
        val gameInfo: Pair<Title, Description> =
            Pair(
                Title("Game Information"),
                Description("Gomoku, also known as Five in a Row, is a classic board game that's easy to learn but challenging to master. The goal is simple: be the first player to get five of your stones in a row horizontally, vertically, or diagonally on the game board.")
            )

        val support: Pair<Title, Description> =
            Pair(
                Title("Feedback and Support"),
                Description("We value your input! If you have questions, suggestions, or run into any issues while using our app, please don't hesitate to reach out to our support team.")
            )

        val authors: Pair<Title, String> =
            Pair(
                Title("Authors"),
                listOf(
                    Author(49513, "Diogo Rodrigues"),
                    Author(48666, "Tiago Frazao"),
                    Author(97643, "Francisco Engenheiro")
                ).joinToString("\n")
            )

    }
}