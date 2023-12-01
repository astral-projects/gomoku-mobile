package gomoku.infrastructure.serializer

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gomoku.domain.variant.VariantConfig

object VariantsGsonSerializer : GsonSerializer<List<VariantConfig>> {
    override fun serialize(data: List<VariantConfig>): String {
        return Gson().toJson(data)
    }

    override fun deserialize(data: String): List<VariantConfig> {
        val type = object : TypeToken<List<VariantConfig>>() {}.type
        return Gson().fromJson(data, type)
    }
}