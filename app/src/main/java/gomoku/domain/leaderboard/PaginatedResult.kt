package gomoku.domain.leaderboard

data class PaginatedResult<R>(
    val items: List<R>,
    val firstPageUrl: String? = null,
    val nextPageUrl: String? = null,
    val previousPageUrl: String? = null,
    val lastPageUrl: String? = null,
)