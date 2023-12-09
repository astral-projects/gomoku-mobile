package gomoku.domain.leaderboard

import java.text.DecimalFormat

object NumberFormatter {
    fun format(num: String): String {
        val number = num.toInt()
        val decimalFormat = DecimalFormat("#.##")
        return when {
            number >= 1000000 -> "${decimalFormat.format(number / 1000000)}M"
            number >= 1000 -> "${decimalFormat.format(number / 1000)}k"
            else -> num
        }
    }
}