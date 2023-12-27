package gomoku.http.models.users

import gomoku.http.models.users.userInfo.Email
import gomoku.http.models.users.userInfo.Id
import gomoku.http.models.users.userInfo.Username
import gomoku.http.models.users.userInfo.ValidationInfo

data class LoginUserOutputModel(
    val id: Id,
    val username: Username,
    val email: Email,
    val passwordValidation: ValidationInfo
)