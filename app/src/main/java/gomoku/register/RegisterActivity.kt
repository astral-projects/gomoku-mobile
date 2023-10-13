package gomoku.register

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalConfiguration
import gomoku.login.LoginActivity
import gomoku.ui.background.BackgroundConfig

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterScreen(
                onSubmit = { LoginActivity.navigateTo(this) },
            )
        }
    }
}