package gomoku.http.service

import gomoku.domain.game.match.Game
import gomoku.domain.game.match.Lobby
import gomoku.domain.game.match.Match
import gomoku.domain.game.moves.Move
import gomoku.domain.login.UserInfo
import gomoku.domain.service.game.GameService
import gomoku.http.media.siren.SirenModel
import gomoku.http.models.games.FindGameInputModel
import gomoku.http.models.games.FindGameOutputModel
import gomoku.http.models.lobby.DeleteLobbyOutputModel
import gomoku.http.models.lobby.WaitingInLobbyOutputModel
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
        val uri = convertTemplateToUrl(
            template, mapOf(
                "game_id" to id.toString()
            )
        )
        TODO("Not yet implemented")
        // Need to have the game model and be careful getting the host and guest that are in entities
        // Need to pass to the siren model the type of the properties and entities. See examples below
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
        TODO("Not yet implemented")
    }

    override suspend fun exitLobby(lobbyId: Int, token: String) {
        val template = findRecipeUri(preferences, "exit-lobby")
        val uri = convertTemplateToUrl(
            template, mapOf(
                "lobby_id" to lobbyId.toString()
            )
        )
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

    override suspend fun waitingInLobby(lobbyId: Int, token: String): Boolean {
        val template = findRecipeUri(preferences, "lobby")
        val uri = convertTemplateToUrl(
            template, mapOf(
                "lobby_id" to lobbyId.toString()
            )
        )
        val res: SirenModel<WaitingInLobbyOutputModel, Unit> =
            callApi<Unit, SirenModel<WaitingInLobbyOutputModel, Unit>>(
                url = uri,
                token = token
            )

        return res.properties.id == lobbyId
    }


}