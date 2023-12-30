package gomoku.infrastructure

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import gomoku.domain.login.UserInfo
import gomoku.domain.storage.PreferencesRepository
import gomoku.domain.variant.VariantConfig
import gomoku.http.utils.recipes.Recipe
import gomoku.infrastructure.serializer.UriTemplatesGsonSerializer
import gomoku.infrastructure.serializer.VariantsGsonSerializer
import kotlinx.coroutines.flow.first

class PreferencesDataStore(
    private val store: DataStore<Preferences>
) : PreferencesRepository {

    companion object {
        private const val USER_KEY = "user"
        private const val USER_NAME_KEY = "$USER_KEY-name"
        private const val USER_ID_KEY = "$USER_KEY-id"
        private const val USER_TOKEN_KEY = "$USER_KEY-token"
        private const val USER_EMAIL_KEY = "$USER_KEY-email"
        private const val ICON_ID_KEY = "$USER_KEY-icon-id"
        private const val DARK_MODE_KEY = "dark-mode"
        private const val VARIANTS_KEY = "variants"
        private const val URI_TEMPLATES_KEY = "uri-templates"
    }

    private val nameKey = stringPreferencesKey(USER_NAME_KEY)
    private val idKey = intPreferencesKey(USER_ID_KEY)
    private val tokenKey = stringPreferencesKey(USER_TOKEN_KEY)
    private val emailKey = stringPreferencesKey(USER_EMAIL_KEY)
    private val iconIdKey = intPreferencesKey(ICON_ID_KEY)
    private val darkModeKey = booleanPreferencesKey(DARK_MODE_KEY)
    private val variantsKey = stringPreferencesKey(VARIANTS_KEY)
    private val uriTemplatesKey = stringPreferencesKey(URI_TEMPLATES_KEY)

    override suspend fun getUserInfo(): UserInfo? {
        val preferences = store.data.first()
        val username = preferences[nameKey]
        val id = preferences[idKey]
        val token = preferences[tokenKey]
        val email = preferences[emailKey]
        val iconId = preferences[iconIdKey]
        Log.v(
            "PreferencesDataStore",
            "username: $username, id: $id, token: $token, email: $email, iconId: $iconId"
        )
        return if (username != null && id != null && token != null && email != null &&
            iconId != null
        ) {
            UserInfo(
                id = id,
                username = username,
                email = email,
                token = token,
                iconId = iconId
            )
        } else {
            null
        }
    }

    override suspend fun setUserInfo(userInfo: UserInfo) {
        store.edit { preferences ->
            preferences[nameKey] = userInfo.username
            preferences[idKey] = userInfo.id
            preferences[tokenKey] = userInfo.token
            preferences[emailKey] = userInfo.email
            preferences[iconIdKey] = userInfo.iconId
        }
    }

    override suspend fun clearUserInfo(userInfo: UserInfo) {
        if (userInfo == getUserInfo()) {
            store.edit { preferences ->
                preferences.remove(nameKey)
                preferences.remove(idKey)
                preferences.remove(tokenKey)
                preferences.remove(emailKey)
                preferences.remove(iconIdKey)
            }
        }
    }

    override suspend fun isInDarkMode(): Boolean? {
        val preferences = store.data.first()
        return preferences[darkModeKey]
    }

    override suspend fun setDarkMode(isInDarkMode: Boolean) {
        store.edit { preferences ->
            preferences[darkModeKey] = isInDarkMode
        }
    }

    override suspend fun getVariants(): List<VariantConfig>? {
        val preferences = store.data.first()
        val variantsString = preferences[variantsKey]
        return variantsString?.let { VariantsGsonSerializer.deserialize(it) }
    }

    override suspend fun setVariants(variants: List<VariantConfig>) {
        store.edit { preferences ->
            preferences[variantsKey] = VariantsGsonSerializer.serialize(variants)
        }
    }

    override suspend fun getUriTemplates(): List<Recipe>? {
        val preferences = store.data.first()
        val uriTemplatesString = preferences[uriTemplatesKey]
        return uriTemplatesString?.let { UriTemplatesGsonSerializer.deserialize(it) }

    }

    override suspend fun setUriTemplates(uriTemplates: List<Recipe>) {
        store.edit { preferences ->
            preferences[uriTemplatesKey] = UriTemplatesGsonSerializer.serialize(uriTemplates)
        }
    }
}