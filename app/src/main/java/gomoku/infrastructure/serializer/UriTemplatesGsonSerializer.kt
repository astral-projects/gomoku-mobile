package gomoku.infrastructure.serializer

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gomoku.domain.service.utils.recipes.Recipe

object UriTemplatesGsonSerializer : GsonSerializer<List<Recipe>> {
    override fun serialize(data: List<Recipe>): String {
        return Gson().toJson(data)
    }

    override fun deserialize(data: String): List<Recipe> {
        val type = object : TypeToken<List<Recipe>>() {}.type
        return Gson().fromJson(data, type)
    }
}