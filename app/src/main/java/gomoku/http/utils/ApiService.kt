package gomoku.http.utils

import com.google.gson.Gson
import okhttp3.OkHttpClient

enum class Method {
    GET, POST, DELETE
}

/**
 * Function that calls the API and returns the response as a json or a problem model.
 * @param method The method to use for the API call.
 * @param url The url to call.
 * @param data The data to send to the API.
 * @param token The token to use for the API call.
 */
suspend inline fun <reified T, reified R> callApi(
    method: Method = Method.GET,
    url: String,
    data: T? = null,
    token: String = ""
): R {
    val apiConnection = ApiConnection(OkHttpClient(), Gson())
    return when (method) {
        Method.GET -> apiConnection.getApi(url, token)
        Method.POST -> apiConnection.postApi(url, data, token)
        Method.DELETE -> apiConnection.deleteApi(url, token)
    }
}
