package gomoku.register

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import gomoku.login.LoginActivity

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