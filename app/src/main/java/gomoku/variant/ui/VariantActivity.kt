package gomoku.variant.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import gomoku.Navigation
import gomoku.game.domain.board.BoardSize
import gomoku.game.ui.GameActivity
import gomoku.variant.domain.OpeningRule
import gomoku.variant.domain.VariantConfig
import gomoku.variant.domain.VariantName

class VariantActivity : ComponentActivity() {
    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
             val intent = Intent(origin, VariantActivity::class.java)
             origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VariantScreen(
                onSubmit = {GameActivity.navigateTo(this)},
                variants = listOf(
                    VariantConfig(
                        name = VariantName.FREESTYLE,
                        boardSize = BoardSize.FIFTEEN,
                        openingRule = OpeningRule.PRO
                    ),
                    VariantConfig(
                        name = VariantName.OMOK,
                        boardSize = BoardSize.NINETEEN,
                        openingRule = OpeningRule.LONG_PRO
                    ),
                    VariantConfig(
                        name = VariantName.PENTE,
                        boardSize = BoardSize.FIFTEEN,
                        openingRule = OpeningRule.PRO
                    ),
                    VariantConfig(
                        name = VariantName.RENJU,
                        boardSize = BoardSize.FIFTEEN,
                        openingRule = OpeningRule.PRO
                    ),
                    VariantConfig(
                        name = VariantName.CARO,
                        boardSize = BoardSize.NINETEEN,
                        openingRule = OpeningRule.PRO
                    )
                )
            )
        }
    }
}