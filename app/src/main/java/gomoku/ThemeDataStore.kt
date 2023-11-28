package gomoku

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first


private const val IS_DARK_THEME_KEY = "IsDarkTheme"

class ThemeDataStore(private val store: DataStore<Preferences>) : ThemeRepository {

    private val isDarkThemeKey = stringPreferencesKey(IS_DARK_THEME_KEY)

    /**
     * Returns the [Boolean] value of the [IS_DARK_THEME_KEY] key.
     * @return the [Boolean] value of the [IS_DARK_THEME_KEY] key.
     * If the key is not present, returns false witch represents the default theme.
     */
    override suspend fun getIsDarkTheme(): Boolean? {
        val preferences = store.data.first()
        val isDarkTheme = preferences[isDarkThemeKey]
        return if (isDarkTheme != null) preferences[isDarkThemeKey]?.let {
            it.toBoolean()
        } else false
    }

    override suspend fun setDarkTheme(isDarkTheme: Boolean) {
        store.edit { preferences ->
            preferences[isDarkThemeKey] = isDarkTheme.toString()
        }
    }
}