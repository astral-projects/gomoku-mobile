package gomoku.http.models.siren

data class Action(
    val name: String,
    val method: String,
    val href: String,
    val title: String,
    val type: String,
    val fields: Array<Field>
)