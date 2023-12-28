package gomoku.http.models.users

data class UsersStatsOutputModel(
    val currentPage: Int,
    val itemsPerPage: Int,
    val totalPages: Int,
    val items: List<UserStatsOutputModel>
)