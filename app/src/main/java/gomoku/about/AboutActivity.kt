package gomoku.about

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboutScreen ({})
        }
    }
}