package gomoku.domain.dialog

import pdm.gomoku.R

object Dialog {
    object UserStats {
        val GAMES = R.string.user_stats_games
        val WINS = R.string.user_stats_wins
        val LOSSES = R.string.user_stats_losses
        val DRAWS = R.string.user_stats_draws
    }

    object GameResults {
        val TITLE = R.string.game_results_title
        val BODY_TEXT_TOP = R.string.game_results_body_text_top
        val BODY_TEXT_BOTTOM = R.string.game_results_body_text_bottom
    }

    object LeaveGame {
        val MESSAGE = R.string.leave_game_message
        val LEAVE_MSG = R.string.leave_game_leave_msg
        val CONTINUE_MSG = R.string.leave_game_continue_msg
        val BACK_HOME = R.string.leave_game_back_home
    }

    object TurnTimeExceed {
        val TITLE = R.string.turn_time_exceed_title
        val BODY_MSG = R.string.turn_time_exceed_body_msg
        val BUTTON_MSG = R.string.turn_time_exceed_button_msg
    }

    object WaitingOpponent {
        val TITLE = R.string.waiting_opponent_title
    }
}