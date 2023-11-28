package gomoku

import android.os.Parcelable
import gomoku.home.ui.HomeActivity
import gomoku.login.UserInfo
import gomoku.login.domain.Username
import kotlinx.parcelize.Parcelize

const val USER_EXTRA = "User"

/**
 * Represents the data to be passed as an extra in the intent that navigates to the
 * [HomeActivity]. We use this class because the [UserInfo] class is not
 * parcelable and we do not want to make it parcelable because it's a domain class.
 */
@Parcelize
data class UserExtra(val id: Int, val username: String, val email: String, val token: String) :
    Parcelable {
    constructor(userInfo: UserInfo) : this(
        userInfo.id,
        userInfo.username.value,
        userInfo.email,
        userInfo.token
    )
}

fun UserExtra.toUserInfo() = UserInfo(id, Username(username), email, token)

