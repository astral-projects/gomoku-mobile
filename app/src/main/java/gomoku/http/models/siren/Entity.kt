package gomoku.http.models.siren

data class Entity<T>(
    val clazz: Array<String>,
    val properties: T,
    val actions: Array<Action>,
    val links: Array<Link>
)