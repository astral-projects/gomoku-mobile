package gomoku.about

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class AboutActivity : ComponentActivity() {

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, AboutActivity::class.java)
            origin.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboutScreen {}
        }
    }
}