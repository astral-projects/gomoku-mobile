package gomoku.http.media.siren

class Action(
    val name: String,
    val method: String,
    val href: String,
    val title: String,
    val type: String,
    val fields: List<Field>
)

class Field(
    val name: String,
    val type: String,
    val value: String
)