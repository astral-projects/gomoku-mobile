package gomoku.ui.register

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
import gomoku.domain.Loaded
import gomoku.domain.idle
import gomoku.ui.Navigation
import gomoku.ui.login.LoginActivity
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
        RegisterViewModel.factory(dependencies.userService, dependencies.preferencesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.userId.collect {
                if (it is Loaded) {
                    LoginActivity.navigateTo(this@RegisterActivity)
                    viewModel.resetToIdle()
                }
            }
        }
        setContent {
            val state by viewModel.userId.collectAsState(initial = idle())
            RegisterScreen(
                state,
                onCreateUser = { username, email, password ->
                    viewModel.register(
                        username,
                        email,
                        password
                    )
                },
            )
        }
    }
}