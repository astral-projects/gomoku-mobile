package gomoku.http.service

import gomoku.domain.game.match.Game
import gomoku.domain.game.match.Lobby
import gomoku.domain.game.match.Match
import gomoku.domain.game.moves.Move
import gomoku.domain.login.UserInfo
import gomoku.domain.service.game.GameService
import gomoku.http.media.siren.SirenModel
import gomoku.http.models.games.ExitGameOutputModel
import gomoku.http.models.games.FindGameInputModel
import gomoku.http.models.games.FindGameOutputModel
import gomoku.http.models.games.GameOutputModel
import gomoku.http.models.games.MoveInputModel
import gomoku.http.models.games.toGame
import gomoku.http.models.lobby.DeleteLobbyOutputModel
import gomoku.http.models.lobby.WaitingInLobbyOutputModel
import gomoku.http.models.users.UserOutputModel
import gomoku.http.utils.Method
import gomoku.http.utils.callApi
import gomoku.http.utils.convertTemplateToUrl
import gomoku.http.utils.recipes.findRecipeUri
import gomoku.infrastructure.PreferencesDataStore

class ApiGameService(
    val preferences: PreferencesDataStore,
) : GameService {

    override suspend fun fetchGameById(id: Int): Game {
        val template = findRecipeUri(preferences, "game")
        val uri = convertTemplateToUrl(template, id.toString())
        val res: SirenModel<GameOutputModel, UserOutputModel> =
            callApi<Unit, SirenModel<GameOutputModel, UserOutputModel>>(url = uri)
        val users = res.entities.map {
            UserOutputModel(
                id = it.properties.id,
                username = it.properties.username,
                email = it.properties.email
            )
        }
        val g = res.properties
        return g.toGame(users)
    }

    override suspend fun findGame(variantId: Int, userInfo: UserInfo): Match {
        val uri = findRecipeUri(preferences, "find-game")
        val res = callApi<FindGameInputModel, SirenModel<FindGameOutputModel, Unit>>(
            method = Method.POST,
            url = uri,
            data = FindGameInputModel(variantId),
            token = userInfo.token
        )

        return if (res.`class`.contains("lobby")) {
            Lobby(
                id = res.properties.id,
                host = userInfo,
                variantId = variantId
            )
        } else if (res.`class`.contains("game")) {
            fetchGameById(res.properties.id)
        } else {
            throw IllegalArgumentException("Unknown match type: ${res.`class`}")
        }

    }

    override suspend fun makeMove(gameId: Int, move: Move, token: String): Game {
        val template = findRecipeUri(preferences, "move")
        val uri = convertTemplateToUrl(template, gameId.toString())
        val res = callApi<MoveInputModel, SirenModel<GameOutputModel, Unit>>(
            method = Method.POST,
            url = uri,
            data = MoveInputModel(move.first.col.letter.toString(), move.first.row.number),
            token = token
        )

        return if (res.`class`.contains("game")) {
            fetchGameById(res.properties.id)
        } else {
            throw IllegalArgumentException("Unknown match type: ${res.`class`}")
        }
    }

    override suspend fun exitLobby(lobbyId: Int, token: String) {
        val template = findRecipeUri(preferences, "exit-lobby")
        val uri = convertTemplateToUrl(template, lobbyId.toString())
        val res: SirenModel<DeleteLobbyOutputModel, Unit> =
            callApi<Unit, SirenModel<DeleteLobbyOutputModel, Unit>>(
                method = Method.DELETE,
                url = uri,
                token = token
            )

        if (res.properties.lobbyId != lobbyId) {
            throw IllegalStateException("The lobby id returned from the server does not match the lobby id sent to the server.")
        }
    }

    override suspend fun waitingInLobby(lobbyId: Int, token: String): Pair<Boolean, Int> {
        val template = findRecipeUri(preferences, "lobby")
        val uri = convertTemplateToUrl(template, lobbyId.toString())
        val res: SirenModel<WaitingInLobbyOutputModel, Unit> =
            callApi<Unit, SirenModel<WaitingInLobbyOutputModel, Unit>>(
                url = uri,
                token = token
            )
        if (res.`class`.contains("lobby")) {
            return Pair(true, res.properties.id)
        } else if (res.`class`.contains("game")) {
            return Pair(false, res.properties.id)
        } else {
            throw IllegalArgumentException("Unknown match type: ${res.`class`}")
        }
    }

    override suspend fun exitGame(gameId: Int, token: String) {
        val template = findRecipeUri(preferences, "exit-game")
        val uri = convertTemplateToUrl(template, gameId.toString())
        val res: SirenModel<ExitGameOutputModel, Unit> =
            callApi<Unit, SirenModel<ExitGameOutputModel, Unit>>(
                method = Method.POST,
                url = uri,
                token = token
            )

        if (res.properties.gameId != gameId) {
            throw IllegalStateException("The game id returned from the server does not match the game id sent to the server.")
        }
    }


}