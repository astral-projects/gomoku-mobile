package gomoku.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import gomoku.Navigation
import gomoku.login.LoginActivity
import gomoku.home.HomeActivity

class RegisterActivity : ComponentActivity() {

    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
            val intent = Intent(origin, RegisterActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterScreen(
                onSubmit = { HomeActivity.navigateTo(this) },
            )
        }
    }
}