package gomoku.login.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import gomoku.Navigation
import gomoku.home.ui.HomeActivity
import gomoku.register.ui.RegisterActivity

class LoginActivity : ComponentActivity() {

    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
            val intent = Intent(origin, LoginActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onSubmit = { _, _ -> HomeActivity.navigateTo(this) },
                onSignUpLinkClick = { RegisterActivity.navigateTo(this) }
            )
        }
    }
}