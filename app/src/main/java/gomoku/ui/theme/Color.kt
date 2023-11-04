package gomoku.ui.theme

import androidx.compose.ui.graphics.Color

// Domain colors
val PaynesGrey = Color(0xFF3e5c76)
val EggShell = Color(0xFFF0ebd8)
val SilverLakeBlue = Color(0xFF748CAB)
val DarkRed = Color(0xFFcc0001)
val Wine = Color(0xFF8a242c)
val LightOrange = Color(0xFFfff2cc)
val LightYellow = Color(0xFFf4d36b)
val DarkOrange = Color(0xFFd79b00)
val LightBlue = Color(0xFFdae8fc)
val DarkBlue = Color(0xFF6c8ebf)
val LightGrey = Color(0xFFbac8d3)
val DarkGreen = Color(0xFF147f58)
val Lime = Color(0xFF98d077)
val Grey = Color(0xFF657a95)
val DarkPurple = Color(0xFF800080)

// Component colors
object BubbleColors {
    val DarkerBubbleInterior = LightOrange
    val DarkerBubbleBorder = DarkOrange
    val LighterBubbleInterior = LightBlue
    val LighterBubbleBorder = DarkBlue
}

object ChipColors {
    val SelectedInterior = Lime
    val SelectedBorder = DarkGreen
    val UnselectedInterior = LightGrey
    val UnselectedBorder = Color.Black
}
