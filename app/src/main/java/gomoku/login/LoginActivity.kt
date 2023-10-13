package gomoku.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalConfiguration
import gomoku.ui.screens.background.BackgroundConfig

class LoginActivity : ComponentActivity() {

    companion object {
        fun navigateTo(origin: Activity) {
            val intent = Intent(origin, LoginActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onSubmit = { /*TODO*/ },
                onSignUpClick = { /*TODO*/ }
            )
        }
    }
}