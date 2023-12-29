package gomoku.http.utils

import android.util.Log

/**
 * Converts a template to an url.
 *
 * Example:
 *
 * ```
 * template: /api/users/{userId}
 * params: {userId: 1}
 * returns: /api/users/1
 * ```
 *
 * ```
 * template: /api/users/{userId}/friends/{friendId}
 * params: {userId: 1, friendId: 2}
 * returns: /api/users/1/friends/2
 * ```
 *
 * ```
 * template: /api/users/stats?q={query}{&page,itemPerPage}
 * params: {query: 'test', page: 1, itemPerPage: 10}
 * returns: /api/users/stats?q=test&page=1&itemPerPage=10
 * ```
 * @param template The template to replace path and query params with.
 * @param params The params to replace.
 * @return The modified uri.
 */
fun convertTemplateToUrl(template: String, params: Map<String, Any>): String {
    Log.v("searchUsers", "template: $template")
    val paramNames = getParamNames(template)
    Log.v("searchUsers", "paramNamesOutside: $paramNames")
    var modifiedUri = template
    paramNames.forEach { paramName ->
        val paramValue = params[paramName]
        // {&page,itemPerPage} => &page=3&itemPerPage=10
        // or {&page} => &page=3
        if (paramName.startsWith("&")) {
            // parse the query params
            // paramName can be [&page,itemsPerPage] or [&page]
            val queryParamNames = paramName.substring(1).split(',')
            var query = ""
            queryParamNames.forEach { queryParamName ->
                val queryParamValue = params[queryParamName]
                if (queryParamValue != null) {
                    query += "&$queryParamName=$queryParamValue"
                }
            }
            Log.v("searchUsers", "query: $query")
            modifiedUri = modifiedUri.replace("{${paramName}}", query)
        } else if (paramValue != null) {
            modifiedUri = modifiedUri.replace("{${paramName}}", paramValue.toString())
        }
    }
    // ensure after ? there is no &
    modifiedUri = modifiedUri.replace("?&", "?")
    Log.v("searchUsers", "modifiedUri: $modifiedUri")
    return modifiedUri
}

/**
 * Returns the param names of the uri, delimited by **{}**.
 *
 * Example:
 *
 * ```
 * uri: /api/users/{userId}/friends/{friendId}
 * returns: [userId, friendId]
 * ```
 *
 * ```
 * uri: /api/users/stats?q={query}{&page,itemPerPage}
 * returns: [query, &page,itemPerPage]
 * ```
 * @param uri to get the param names
 * @return An array of param names.
 */
fun getParamNames(uri: String): List<String> {
    val paramNames = mutableListOf<String>()
    val regex = "\\{([^{}]+)\\}".toRegex()
    val matches = regex.findAll(uri)
    for (match in matches) {
        paramNames.add(match.groupValues[1])
    }
    return paramNames
}

