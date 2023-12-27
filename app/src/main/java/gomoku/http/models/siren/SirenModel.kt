package gomoku.http.models.siren

data class SirenModel<T>(
    val clazz: Array<String>,
    val properties: T,
    val entities: Array<Entity<*>>,
    val actions: Array<Action>,
    val links: Array<Link>,
    val receiptLinks: Array<Link>
)
