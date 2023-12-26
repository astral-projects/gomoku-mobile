package gomoku

import com.google.gson.Gson
import gomoku.domain.service.game.GameService
import gomoku.domain.service.user.UserServiceInterface
import gomoku.domain.service.variant.VariantService
import gomoku.domain.storage.PreferencesRepository
import okhttp3.OkHttpClient

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
     * The data store used to store the user info
     */
    val preferencesRepository: PreferencesRepository

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
    val userServiceInterface: UserServiceInterface
}
