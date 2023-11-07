package gomoku.shared.popups

object Popup {
    object GameResults {
        const val TITLE = "Results"
        const val BODY_TEXT_TOP = "Winner"
        const val BODY_TEXT_BOTTOM = "Points"
    }
    object LeaveGame {
        const val MESSAGE =
            "Are you sure you want to quit this game? You won't receive any points as a result."
        const val LEAVE_MSG = "Yes"
        const val CONTINUE_MSG = "Nevermind"
    }
    object TurnTimeExceed {
        const val TITLE = "Turn Time Exceeded"
        const val BODY_MSG =
            "Your strategic moves were on point, but this time, the clock got better of you. Unfortunately, no points can be awarded as a result."
        const val BUTTON_MSG = "Acknowledge"
    }

    object WaitingOpponent {
        const val TITLE = "Waiting for an opponent"
    }
}