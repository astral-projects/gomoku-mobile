package gomoku

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import gomoku.domain.game.board.Board
import gomoku.domain.game.moves.move.Player
import gomoku.domain.service.game.GameService
import gomoku.domain.service.user.UserService
import gomoku.domain.service.variant.VariantService
import gomoku.http.models.Turn
import gomoku.http.models.games.BoardOutputModel
import gomoku.http.service.ApiGameService
import gomoku.http.service.ApiUserService
import gomoku.http.service.ApiVariantsService
import gomoku.infrastructure.PreferencesDataStore
import okhttp3.OkHttpClient
import java.lang.reflect.Type
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
    override val gson: Gson = GsonBuilder()
        .registerTypeAdapter(
            Board::class.java,
            BoardDeserializer()
        ).create()

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


class BoardDeserializer() : JsonDeserializer<BoardOutputModel> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): BoardOutputModel {
        val jsonObject = json.asJsonObject
        val grid = jsonObject.getAsJsonArray("grid").map { it.asString }
        val boardTurn = jsonObject.getAsJsonObject("turn").let {
            context.deserialize<Turn>(it, Turn::class.java)
        }
        val winner = jsonObject.getAsJsonPrimitive("winner")?.let { Player.valueOf(it.asString) }
        return BoardOutputModel(
            grid = grid,
            turn = boardTurn,
            winner = winner?.name,
        )
    }
}
