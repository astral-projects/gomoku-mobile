package gomoku.login.ui

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
import gomoku.getOrNull
import gomoku.home.ui.HomeActivity
import gomoku.idle
import gomoku.login.UserInfo
import gomoku.register.ui.RegisterActivity
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
            dependencies.userService,
            dependencies.userInfoRepository,
            dependencies.themeRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.userInfo.collect {
                if (it is Loaded && it.value.isSuccess) {
                    doNavigation(userInfo = it.getOrNull())
                    viewModel.resetToIdle()
                } else if (it is Loaded && it.value.isFailure) {
                    viewModel.resetToIdle()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isDarkTheme.collect {
                viewModel.isDarkTheme()
            }
        }

        setContent {
            val state by viewModel.userInfo.collectAsState(initial = idle())
            val isDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            LoginScreen(
                inDarkTheme = isDarkTheme,
                authenticatedUserInfo = state,
                onSubmit = { username, password ->
                    viewModel.fetchLogin(username, password)
                },
                onSignUpLinkClick = { RegisterActivity.navigateTo(this) }
            )
        }
    }

    /**
     * Navigates to the appropriate activity, depending on whether the
     * user information has already been provided or not.
     * @param userInfo the user information.
     */
    private fun doNavigation(userInfo: UserInfo?) {
        if (userInfo != null)
            HomeActivity.navigateTo(this@LoginActivity, userInfo)
    }

}