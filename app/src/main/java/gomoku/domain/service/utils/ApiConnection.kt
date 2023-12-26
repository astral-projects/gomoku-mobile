package gomoku.domain.service.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gomoku.domain.service.media.ProblemModel
import gomoku.domain.service.media.siren.SirenModel
import gomoku.domain.service.utils.recipes.URI
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Defines the behavior of an API connection.
 */
class ApiConnection(
    val client: OkHttpClient,
    val gson: Gson
) {

    /**
     * Function to get the data from the given url.
     * @param url The url to get the data from.
     */
    suspend inline fun <reified T> getApi(url: String, token: String): T =
        suspendCoroutine { continuation ->
            val request = Request
                .Builder()
                .url("${URI}$url")
                .addHeader("Authorization", "Bearer $token")
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body
                    if (responseBody != null) {
                        if (!response.isSuccessful) {
                            val failResult =
                                gson.fromJson(responseBody.string(), ProblemModel::class.java)
                            continuation.resumeWithException(IOException(failResult.detail))
                        } else {
                            val result = gson.fromJson<SirenModel<T>>(
                                responseBody.string(),
                                SirenModel::class.java
                            )
                            continuation.resume(result.properties)
                        }
                    }
                }
            })
        }


    /**
     * Function to post the data to the given url.
     * @param url The url to post the data to.
     * @param data The data to post to the given url.
     */
    suspend inline fun <reified T, R> postApi(url: String, data: T, token: String): R {
        val json = gson.toJson(data)
        val body = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val request = Request
            .Builder()
            .url("${URI}$url")
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .post(body)
            .build()

        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.v("Login", e.toString())
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body
                    if (responseBody != null) {
                        if (!response.isSuccessful) {
                            val failResult =
                                gson.fromJson(responseBody.string(), ProblemModel::class.java)
                            continuation.resumeWithException(IOException(failResult.detail))
                        } else {
                            val result = gson.fromJson<SirenModel<R>>(
                                responseBody.string(),
                                SirenModel::class.java
                            )
                            continuation.resume(result.properties)
                        }
                    }
                }
            })
        }
    }

    /**
     * Function to delete the data from the given url.
     * @param url The url to delete the data from.
     */
    suspend inline fun <reified T> deleteApi(url: String, token: String): T {
        val request = Request
            .Builder()
            .url("${URI}$url")
            .addHeader("Authorization", "Bearer $token")
            .delete()
            .build()

        return suspendCoroutine { continuation ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) continuation.resumeWithException(IOException("Unexpected code $response"))
                    val responseBody = response.body?.string()
                    val result = gson.fromJson<T>(responseBody, object : TypeToken<T>() {}.type)
                    continuation.resume(result)
                }
            })
        }

    }
}