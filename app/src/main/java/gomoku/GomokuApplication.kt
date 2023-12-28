package gomoku

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import gomoku.domain.service.game.GameService
import gomoku.domain.service.user.UserService
import gomoku.domain.service.variant.VariantService
import gomoku.http.service.ApiGameService
import gomoku.http.service.ApiUserService
import gomoku.http.service.ApiVariantsService
import gomoku.infrastructure.PreferencesDataStore
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * The application's class used to resolve dependencies, acting as a Service Locator.
 * Dependencies are then injected manually by each Android Component (e.g Activity, Service, etc.).
 */
class GomokuApplication : Application(), GomokuDependencyProvider {

    companion object {
        /**
         * The timeout for HTTP requests
         */
        private const val timeout = 10L
        const val GOMOKU_DATASTORE = "gomoku_datastore"
    }

    private val dataStore: DataStore<Preferences> by preferencesDataStore(GOMOKU_DATASTORE)

    override val preferencesRepository: PreferencesDataStore
        get() = PreferencesDataStore(dataStore)

    /**
     * The HTTP client used to perform HTTP requests
     */
    override val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .callTimeout(timeout, TimeUnit.SECONDS)
            .build()

    /**
     * The JSON serializer/deserializer used to convert JSON into DTOs
     */
    override val gson: Gson = Gson()

    override val gameService: GameService by lazy {
        ApiGameService(preferencesRepository)
    }

    override val variantService: VariantService by lazy {
        ApiVariantsService(preferencesRepository)
    }

    // How can i pass the preferencesRepository to the UserService? I can't use the constructor
    // because the UserService is created at the runtime of the application.
    override val userService: UserService by lazy {
        ApiUserService(preferencesRepository)
    }
}