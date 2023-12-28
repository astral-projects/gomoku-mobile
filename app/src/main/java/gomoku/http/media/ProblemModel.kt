package gomoku.http.media

class ProblemModel(
    val type: String,
    val title: String,
    val status: Int,
    val detail: String,
    val instance: String,
    val data: String?
)