package gomoku

import gomoku.login.UserInfo

interface UserInfoRepository {
    suspend fun getUserInfo(): UserInfo?

    suspend fun updateUserInfo(userInfo: UserInfo)
}