package gomoku.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import gomoku.GomokuDependencyProvider
import gomoku.ui.Navigation
import gomoku.ui.home.HomeActivity
import gomoku.ui.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
            val intent = Intent(origin, LoginActivity::class.java)
            origin.startActivity(intent)
        }
    }

    /**
     * The application's dependency provider.
     */
    private val dependencies by lazy { application as GomokuDependencyProvider }

    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModel.factory(
            dependencies.userServiceInterface,
            dependencies.preferencesRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.stateFlow.collect {
                if (it is LoginScreenState.Login && it.isLoggedIn) {
                    doNavigation(userName = it.userInfo?.username ?: "")
                    viewModel.resetToIdle()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect {
                    if (it is LoginScreenState.Idle) {
                        viewModel.fetchUriTemplates()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isDarkTheme.collect {
                    viewModel.isDarkTheme()
                }
            }

        }
        setContent {
            val state = viewModel.stateFlow.collectAsState(initial = LoginScreenState.Idle).value
            val isDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            LoginScreen(
                isLoggingIn = state is LoginScreenState.Login && !state.isLoggedIn,
                inDarkTheme = isDarkTheme,
                authenticatedUserInfo = state,
                onSubmit = { username, password ->
                    viewModel.login(username, password)
                },
                onSignUpLinkClick = { RegisterActivity.navigateTo(this) }
            )
        }
    }

    /**
     * Navigates to the appropriate activity, depending on whether the
     * user information has already been provided or not.
     * @param userName the user information.
     */
    private fun doNavigation(userName: String) {
        HomeActivity.navigateTo(this@LoginActivity, userName)
    }

}