package gomoku.http.utils

/**
 * This function is used to convert a template to a url.
 * @param template The template to be converted.
 * @param values The values to be inserted into the template.
 */
fun convertTemplateToUrl(template: String, vararg values: String): String {
    var url = template
    var index = 0

    // Replace path variables
    while (url.contains("{") && url.contains("}") && index < values.size) {
        url = url.replaceFirst(Regex("\\{.*?\\}"), values[index])
        index++
    }

    // Replace query parameters
    if (url.contains("{&") && url.contains("}")) {
        val queryParams = StringBuilder()
        while (index < values.size) {
            queryParams.append("&${values[index]}")
            index++
        }
        url = url.replace(Regex("\\{&.*?\\}"), queryParams.toString())
    }

    return url
}
