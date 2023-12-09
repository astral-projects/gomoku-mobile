package gomoku

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import gomoku.game.FakeGameService
import gomoku.game.GameService
import gomoku.http.GameServiceImplementation
import gomoku.http.UserServiceImplementation
import gomoku.http.VariantServiceImplementation
import gomoku.leaderboard.user.FakeUserServices
import gomoku.leaderboard.user.UserService
import gomoku.variant.FakeVariantService
import gomoku.variant.VariantService
import gomoku.variant.VariantsInfoRepositroy
import okhttp3.OkHttpClient

/**
 * The tag used to identify log messages across the application. Here we elected to use the same
 * tag for all log messages.
 */
const val TAG = "JOKES_APP_TAG"

/**
 * The contract to be supported by the application's class used to resolve dependencies.
 */
interface GomokuDependencyProvider {
    /**
     * The HTTP client used to perform HTTP requests
     */
    val httpClient: OkHttpClient

    /**
     * The JSON serializer/deserializer used to convert JSON into DTOs
     */
    val gson: Gson

    /**
     * The service used to fetch games
     */
    val gameService: GameService

    /**
     * The service used to fetch variants
     */
    val variantService: VariantService

    /**
     * The service used to fetch users
     */
    val userService: UserService

    val userInfoRepository: UserInfoRepository

    val variantsInfoRepository: VariantsInfoRepositroy

    val themeRepository: ThemeRepository
}

//TODO(Separate this class)
/**
 * The application's class used to resolve dependencies, acting as a Service Locator.
 * Dependencies are then injected manually by each Android Component (e.g Activity, Service, etc.).
 */
class GomokuApplication : Application(), GomokuDependencyProvider {

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_info")


    override val userInfoRepository: UserInfoRepository
        get() = UserInfoDataStore(dataStore)

    override val variantsInfoRepository: VariantsInfoRepositroy
        get() = VariantDataStore(dataStore)

    override val themeRepository: ThemeRepository
        get() = ThemeDataStore(dataStore)

    /**
     * The HTTP client used to perform HTTP requests
     */
    override val httpClient: OkHttpClient =
        OkHttpClient.Builder()
            .callTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
            .build()

    /**
     * The JSON serializer/deserializer used to convert JSON into DTOs
     */
    override val gson: Gson = Gson()

    /**
     * The service used to fetch jokes
     */
    override val gameService: GameService =
        GameServiceImplementation(
            listOf(
                FakeGameService(),
                //GomokuGame(httpClient, gson),
            )
        )

    override val variantService: VariantService =
        VariantServiceImplementation(
            listOf(
                FakeVariantService()
            )
        )
    override val userService: UserService =
        UserServiceImplementation(
            listOf(
                FakeUserServices()
            )
        )
}