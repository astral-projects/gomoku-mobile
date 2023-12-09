package gomoku.domain.storage

import gomoku.domain.login.UserInfo
import gomoku.domain.variant.VariantConfig

/**
 * Contract to be supported by the user info repository.
 * The user repository is the authority for the user info application state.
 */
interface PreferencesRepository {

    /**
     * Gets the user info if it exists, null otherwise.
     */
    suspend fun getUserInfo(): UserInfo?

    /**
     * Updates the user info.
     */
    suspend fun setUserInfo(userInfo: UserInfo)

    /**
     * Gets the dark mode state if it exists, null otherwise.
     */
    suspend fun isInDarkMode(): Boolean?

    /**
     * Updates the dark mode state.
     */
    suspend fun setDarkMode(isInDarkMode: Boolean)

    /**
     * Gets the variants if they exist, null otherwise.
     */
    suspend fun getVariants(): List<VariantConfig>?

    /**
     * Updates the variants.
     */
    suspend fun setVariants(variants: List<VariantConfig>)
}