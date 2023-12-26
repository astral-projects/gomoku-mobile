package gomoku.domain.service.utils

import com.google.gson.Gson
import okhttp3.OkHttpClient

enum class Method {
    GET, POST, DELETE
}

/**
 * Function that calls the API and returns the response as a json or a problem model.
 * @param uri - the uri to call
 * @param method - the method to use for the request
 * @param body - optional body for POST and PUT requests
 * @param token - optional token for authentication
 */
suspend inline fun <reified T, reified R> callApi(
    method: Method = Method.GET,
    url: String,
    data: T? = null,
    token: String = ""
): R {
    val apiConnection = ApiConnection(OkHttpClient(), Gson())
    return when (method) {
        Method.GET -> apiConnection.getApi<T>(url, token) as R
        Method.POST -> apiConnection.postApi(url, data, token)
        Method.DELETE -> apiConnection.deleteApi(url, token) as R
    }
}
