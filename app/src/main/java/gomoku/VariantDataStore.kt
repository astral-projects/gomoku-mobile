package gomoku

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gomoku.variant.VariantsInfoRepositroy
import gomoku.variant.domain.VariantConfig
import kotlinx.coroutines.flow.first


private val variantsKey = stringPreferencesKey("VARIANTS")

class VariantDataStore(private val store: DataStore<Preferences>) : VariantsInfoRepositroy {


    override suspend fun getVariantsInfo(): List<VariantConfig>? {
        val preferences = store.data.first()
        val variantsString = preferences[variantsKey]
        return variantsString?.let { deserializeVariantList(it) }
    }

    override suspend fun updateVariantsInfo(variantsInfo: List<VariantConfig>) {
        store.edit { preferences ->
            val variantsString = serializeVariantList(variantsInfo)
            preferences[variantsKey] = variantsString
        }
    }
}

private fun serializeVariantList(variantList: List<VariantConfig>): String {
    return Gson().toJson(variantList)
}

private fun deserializeVariantList(variantString: String): List<VariantConfig> {
    val type = object : TypeToken<List<VariantConfig>>() {}.type
    return Gson().fromJson(variantString, type)
}