package gomoku

interface ThemeRepository {
    suspend fun getIsDarkTheme(): Boolean?

    suspend fun setDarkTheme(isDarkTheme: Boolean)
}