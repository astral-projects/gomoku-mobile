package gomoku.http.providers

/**
private const val GOMOKU_BASE_API_URL = "https://localhost:8080/api/"
private const val GET_USERS_STATS = "${GOMOKU_BASE_API_URL}users/stats"
private const val GET_USER = "${GOMOKU_BASE_API_URL}users/{id}"
private const val GET_USER_STATS = "${GOMOKU_BASE_API_URL}users/{id}/stats"

class Users(
    private val client: OkHttpClient,
    private val gson: Gson,
    private val usersUrl: URL = URL(GET_USERS_STATS),
    private val userUrl: URL = URL(GET_USER)

) : UsersServices {

    // implement a request for fetch a user by id in path GET_USER
    private val request = Request.Builder()
        .url(userUrl)
        .get()
        .build()

    override suspend fun fetchUsers(): List<PlayerInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUser(id: Int): PlayerInfo =
        suspendCoroutine {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(FetchUserException("Could not fetch user", e))
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body
                    if (!response.isSuccessful || body == null)
                        it.resumeWithException(FetchUserException("Could not fetch user. Remote server returned ${response.code}"))
                    else {
                        val dto = gson.fromJson(body.string(), UserDto::class.java)
                        it.resumeWith(Result.success(dto.toUser()))
                    }
                }

            })
        }

    override suspend fun fetchUsersStats(): List<RankingInfo> {
        TODO("Not yet implemented")
    }

    private data class UserDto(
        val id: Int,
        val username: String,
        val email: String,
    ) {
        fun toUser() {
            TODO("Not yet implemented")
        }
    }
*/