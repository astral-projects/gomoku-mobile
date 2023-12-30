package gomoku.http.utils.recipes

import android.util.Log
import com.google.gson.Gson
import gomoku.http.media.siren.Link
import gomoku.http.media.siren.SirenModel
import gomoku.infrastructure.PreferencesDataStore
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

const val URI = "https://4e2e-2001-818-dd0a-8c00-e17d-3cf3-afde-ee8e.ngrok-free.app"

suspend fun fetchRecipes(): List<Recipe> {
    val gson = Gson()
    val client = OkHttpClient()
    return suspendCoroutine { continuation ->
        val request = Request
            .Builder()
            .url("$URI/api/")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                continuation.resumeWithException(e)
                Log.v("UriTemplates", "Failed to fetch uri templates")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    continuation.resumeWithException(IOException("Unsuccessful operation"))
                    Log.v("UriTemplates", "Unexpected code $response")
                } else {
                    val body = response.body
                    if (body != null) {
                        val data: List<Link> =
                            gson.fromJson(body.string(), SirenModel::class.java).recipeLinks
                        val toRecipes = data.map { Recipe(it.rel.first(), it.href) }
                        continuation.resume(toRecipes)
                        Log.v("UriTemplates", "Fetched uri templates")
                    } else {
                        continuation.resumeWithException(IOException("Empty response"))
                        Log.v("UriTemplates", "Empty response")
                    }
                }
            }
        })
    }
}

/**
 * Search for a recipe uri stored in the data store.
 * @param preferences The data store to search in.
 * @param rel The name of the relation to search for.
 * @returns The Href of the recipe uri
 */
suspend fun findRecipeUri(preferences: PreferencesDataStore, rel: String): String {
    val uris = preferences.getUriTemplates()
    return uris?.find { it.rel.contains(rel) }?.href ?: "No recipe found"
}

/**
 * Replace the variable params in the uri template
 * Ex.: href = /api/users/{usersId} -> /api/users/123
 * Ex.: href = /api/users/{usersId}/games/{gameId} -> /api/users/123/games/456
 * Ex.: /api/users/stats?q={query}{&page,itemPerPage} with {query: 'test', page: 1, itemPerPage: 10} => /api/users/stats?q=test&page=1&itemPerPage=10
 * @param href - The uri template to replace the params in.
 * @param params - The params to replace.
 */
fun replaceParams(href: String, params: Map<String, String>): String {
    var newHref = href
    params.forEach { (key, value) ->
        newHref = newHref.replace("{$key}", value)
    }
    return newHref
}


