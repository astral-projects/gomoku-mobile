package gomoku.leaderboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import gomoku.Navigation
import gomoku.about.ui.AboutActivity
import gomoku.leaderboard.domain.Leaderboard
import gomoku.login.ui.LoginActivity
import gomoku.variant.ui.VariantActivity

class LeaderboardActivity : ComponentActivity() {

    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
            val intent = Intent(origin, LeaderboardActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val list = Leaderboard.generateRankingInfo(200)
            LeaderboardScreen(
                playerInfo = Leaderboard.fakePlayers.first(),
                getItemsFromPage = { page ->
                    Leaderboard.paginatedRankingInfo(
                        list = list,
                        page = page
                    )
                },
                onSearchRequest = { term ->
                    list.filter { it.playerInfo.name.contains(term.value, ignoreCase = true) }
                },
                toFindGameScreen = { VariantActivity.navigateTo(this) },
                toAboutScreen = { AboutActivity.navigateTo(this) },
                onLogoutRequest = { LoginActivity.navigateTo(this) }
            )
        }
    }
}