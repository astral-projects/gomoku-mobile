package gomoku.register.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import gomoku.GomokuDependencyProvider
import gomoku.Loaded
import gomoku.Navigation
import gomoku.idle
import gomoku.login.ui.LoginActivity
import kotlinx.coroutines.launch

class RegisterActivity : ComponentActivity() {

    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
            val intent = Intent(origin, RegisterActivity::class.java)
            origin.startActivity(intent)
        }
    }

    /**
     * The application's dependency provider.
     */
    private val dependencies by lazy { application as GomokuDependencyProvider }


    private val viewModel by viewModels<RegisterViewModel> {
        RegisterViewModel.factory(dependencies.userService, dependencies.userInfoRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.userInfo.collect {
                if (it is Loaded) {
                    LoginActivity.navigateTo(this@RegisterActivity)
                    viewModel.resetToIdle()
                }
            }
        }
        setContent {
            val state by viewModel.userInfo.collectAsState(initial = idle())
            RegisterScreen(
                state,
                onCreateUser = { username, email, password, confirmPassword ->
                    viewModel.fetchCreateUser(
                        username,
                        email,
                        password,
                        confirmPassword
                    )
                },
            )
        }
    }
}