package gomoku

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import gomoku.login.UserInfo
import gomoku.login.domain.Username
import kotlinx.coroutines.flow.first


private const val USER_NAME_KEY = "Name"
private const val USER_ID_KEY = "id"
private const val USER_TOKEN_KEY = "token"
private const val USER_EMAIL_KEY = "email"

class UserInfoDataStore(private val store: DataStore<Preferences>) : UserInfoRepository {

    private val nameKey = stringPreferencesKey(USER_NAME_KEY)
    private val idKey = intPreferencesKey(USER_ID_KEY)
    private val tokenKey = stringPreferencesKey(USER_TOKEN_KEY)
    private val emailKey = stringPreferencesKey(USER_EMAIL_KEY)

    override suspend fun getUserInfo(): UserInfo? {
        val preferences = store.data.first()
        val nick = preferences[nameKey]
        val id = preferences[idKey]
        val token = preferences[tokenKey]
        val email = preferences[emailKey]
        return if (nick != null && id != null && token != null && email != null) preferences[idKey]?.let {
            UserInfo(
                it,
                Username(nick),
                email,
                token
            )
        } else null
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        store.edit { preferences ->
            preferences[nameKey] = userInfo.username.value
            userInfo.id.let {
                preferences[idKey] = it
            } ?: preferences.remove(idKey)
        }
    }
}